package com.shash.ssh.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.jcraft.jsch.JSchException;

public class WordSenseDisambiguator {

	private String input;

	private String polysemyWord;

	private Multimap<String, String> word_pos_map;

	private List<String[]> semanticsList;
	
	private LinkedList<String> inputLinkedList;
	
	private String matchedLongestWordForNoun;
	
	private String matchedSemanticSentenceForNoun;

	private String matchedLongestWordForVerb;
	
	private String matchedSemanticSentenceForVerb;
	

	public static void main(String[] args) throws JSchException, IOException {

		WordSenseDisambiguator disambiguator = new WordSenseDisambiguator();
		disambiguator.assignPOSTagging();
		disambiguator.extractPOSTagging();
		disambiguator.identifyPolysemyWord();
		disambiguator.readFromWordNet();
		disambiguator.obtainSemanticNet();
		disambiguator.aataEegaShuru();

	}

	private void aataEegaShuru() {
		System.out.println("*********************");
		System.out.println("Input is " + inputLinkedList.toString());
		System.out.println("Polysemy word = " + polysemyWord);
		Collection<Entry<String, String>> entries = word_pos_map.entries();
		entries.forEach(item -> System.out.println(item.getKey() + " :: "
				+ item.getValue()));
		for (String[] semanticUnit : semanticsList) {
			System.out.println("POS = " + semanticUnit[0]);
			System.out.println("Semantic = " + semanticUnit[1]);
		}
		System.out.println("*********************");
		String matchingWordForNoun = null;
		String matchingWordForVerb = null;
		for(Entry<String, String> entry:entries){
			if(entry.getKey().contains(polysemyWord)){
				if(entry.getValue().equals("NN")){
					for(String[] semanticUnit :semanticsList){
						if(semanticUnit[0].equals("noun")){
							String tokensOtherThanPolysemy=null;
							if(semanticUnit[1].contains(polysemyWord)){
								//tokensOtherThanPolysemy=semanticUnit[1].replaceAll(polysemyWord, "");
								tokensOtherThanPolysemy=semanticUnit[1];
								String[] splitOtherTokens = tokensOtherThanPolysemy.split(" ");
								List<String> otherTokensList = Arrays.asList(splitOtherTokens);
								LinkedList<String> otherTokensLinkedList = new LinkedList<String>();
								otherTokensLinkedList.addAll(otherTokensList);
								while(otherTokensLinkedList.contains(polysemyWord)){
									otherTokensLinkedList.remove(polysemyWord);
								}
								System.out.println("Replaced Semantics for Noun is "+otherTokensLinkedList);
								for(String nounSemanticMatcher:inputLinkedList){
									for(String otherSemanticToken:otherTokensLinkedList){
										int length =0;
										if(nounSemanticMatcher.length()<otherSemanticToken.length()){
											length = nounSemanticMatcher.length();
										}
										else{
											length = otherSemanticToken.length();
										}
										int matchLength = 0;
										boolean prestine = true;
										for (int j = 0; j < length; j++) {
											if (nounSemanticMatcher.charAt(j) == otherSemanticToken.charAt(j)) {
												continue;
											} else {
												matchLength = j;
												prestine = false;
												break;
											}
										}
										if (prestine) {
											matchingWordForNoun = nounSemanticMatcher;
											if(!matchingWordForNoun.contains(polysemyWord)){
												if(matchedLongestWordForNoun==null||matchedLongestWordForNoun.length()<matchingWordForNoun.length()){
													matchedLongestWordForNoun=matchingWordForNoun;
													matchedSemanticSentenceForNoun=tokensOtherThanPolysemy;
												}
												break;
											}
										}
										if (matchLength > 2) {
											matchingWordForNoun = otherSemanticToken.substring(0, matchLength);
											if(!matchingWordForNoun.contains(polysemyWord)){
												if(matchedLongestWordForNoun==null||matchedLongestWordForNoun.length()<matchingWordForNoun.length()){
													matchedLongestWordForNoun=matchingWordForNoun;
													matchedSemanticSentenceForNoun=tokensOtherThanPolysemy;
												}
												break;
											}
											
										}
									}
								}
							}
						}
						else{
							continue;
						}
					}
					System.out.println("Matching word is "+matchedLongestWordForNoun);
					System.out.println("Matched meaning is "+matchedSemanticSentenceForNoun);
				}
				else if(entry.getValue().equals("VM")){
					for(String[] semanticUnit :semanticsList){
						if(semanticUnit[0].equals("verb")){
							String tokensOtherThanPolysemy=null;
							if(semanticUnit[1].contains(polysemyWord)){
								tokensOtherThanPolysemy=semanticUnit[1];
								String[] splitOtherTokens = tokensOtherThanPolysemy.split(" ");
								List<String> otherTokensList = Arrays.asList(splitOtherTokens);
								LinkedList<String> otherTokensLinkedList = new LinkedList<String>();
								otherTokensLinkedList.addAll(otherTokensList);
								System.out.println("Replaced Semantics for Verb is "+otherTokensLinkedList);
								for(String verbSemanticMatcher:inputLinkedList){
									for(String otherSemanticToken:otherTokensLinkedList){
										int length =0;
										if(verbSemanticMatcher.length()<otherSemanticToken.length()){
											length = verbSemanticMatcher.length();
										}
										else{
											length = otherSemanticToken.length();
										}
										int matchLength = 0;
										boolean prestine = true;
										for (int j = 0; j < length; j++) {
											if (verbSemanticMatcher.charAt(j) == otherSemanticToken.charAt(j)) {
												continue;
											} else {
												matchLength = j;
												prestine = false;
												break;
											}
										}
										if (prestine) {
											matchingWordForVerb = verbSemanticMatcher;
											if(matchingWordForVerb.contains(polysemyWord)){
												if(matchedLongestWordForVerb==null||matchedLongestWordForVerb.length()<matchingWordForVerb.length()){
													matchedLongestWordForVerb=matchingWordForVerb;
													matchedSemanticSentenceForVerb=tokensOtherThanPolysemy;
												}
												break;
											}
										}
										if (matchLength > 2) {
											matchingWordForVerb = otherSemanticToken.substring(0, matchLength);
											if(!matchingWordForVerb.contains(polysemyWord)){
												if(matchedLongestWordForVerb==null||matchedLongestWordForVerb.length()<matchingWordForVerb.length()){
													matchedLongestWordForVerb=matchingWordForVerb;
													matchedSemanticSentenceForVerb=tokensOtherThanPolysemy;
												}
												break;
											}
											
										}
									}
								}
							}
						}
						else{
							continue;
						}
					}
					System.out.println("Matching word is "+matchedLongestWordForVerb);
					System.out.println("Matched meaning is "+matchedSemanticSentenceForVerb);
				}
			}
		}
	}

	private void readFromWordNet() {
		ReadFromWordnet readWordNet = new ReadFromWordnet();
		readWordNet.readFromWordNet();

	}

	private void identifyPolysemyWord() throws IOException {
		List<String> inputWordsList = null;
		BufferedReader brInput = new BufferedReader(new FileReader("sen31"));

		input = brInput.readLine();
		input = input.replaceAll("[-+.^:,]", "");
		String[] splitWords = input.split(" ");
		inputWordsList = Arrays.asList(splitWords);
		inputLinkedList = new LinkedList<String>();
		inputLinkedList.addAll(inputWordsList);

		for (int i = 0; i < inputLinkedList.size(); i++) {
			String word = inputLinkedList.get(i);
			inputLinkedList.remove(i);
			// System.out.println(linkedList);
			for (String otherToken : inputLinkedList) {
				int length = word.length();
				int matchLength = 0;
				boolean prestine = true;
				for (int j = 0; j < length; j++) {
					if (word.charAt(j) == otherToken.charAt(j)) {
						continue;
					} else {
						matchLength = j;
						prestine = false;
						break;
					}
				}
				if (prestine) {
					polysemyWord = word;
					break;
				}
				if (matchLength > 1) {
					polysemyWord = otherToken.substring(0, matchLength);
					break;
				}
			}
			inputLinkedList.add(i, word);
		}
		// System.out.println("Polysemy word is " + polysemyWord);
	}

	private void extractPOSTagging() throws IOException {
		List<String> inputWordsList = null;
		BufferedReader brInput = new BufferedReader(new FileReader("sen31"));
		while (true) {
			String line = brInput.readLine();
			if (line == null)
				break;
			line = line.replaceAll("[-+.^:,]", "");
			String[] splitWords = line.split(" ");
			inputWordsList = Arrays.asList(splitWords);
			// System.out.println(inputWordsList);
		}
		word_pos_map = LinkedListMultimap.create();
		BufferedReader br = new BufferedReader(new FileReader("out3"));
		while (true) {
			String line = br.readLine();
			if (line == null)
				break;

			if (line.contains("Sentence") || line.contains("((")
					|| line.contains("))") || line.contains("SYM")) {
				continue;
			} else {
				// System.out.println(line);
				String[] split = line.split("<");
				// System.out.println(split[0]);
				String indexedToken = split[0];
				String[] word_pos = indexedToken.split("\\s");
				/*
				 * System.out.println(word_pos[1]);
				 * System.out.println(word_pos[2]);
				 */
				word_pos_map.put(word_pos[1], word_pos[2]);
			}

		}
		/*
		 * Collection<Entry<String, String>> entries = word_pos_map.entries();
		 * entries.forEach(item -> System.out.println(item.getKey() + " :: " +
		 * item.getValue()));
		 */

	}

	private void assignPOSTagging() throws JSchException, IOException {
		java.util.Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", "no");
		config.put("PreferredAuthentications", "password");
		TagPos schObj = new TagPos();
		schObj.sshCon(config);

	}

	private void obtainSemanticNet() throws IOException {
		BufferedReader brInput = new BufferedReader(new FileReader(
				"message.txt"));
		String input = "";
		while (true) {
			String line = brInput.readLine();
			if (line == null)
				break;
			input = input + line;
		}
		// System.out.println(input);
		String[] synsets = null;
		if (input.contains("----------------------------")) {
			synsets = input.split("----------------------------");
		}
		semanticsList = new ArrayList<String[]>();
		if (synsets != null) {

			for (String synset : synsets) {
				// System.out.println(synset);
				if (synset.contains("::::::")) {
					String[] synsetFragments = synset.split("::::::");
					String pos = synsetFragments[0];
					String synonyms = synsetFragments[1];
					String gloss = synsetFragments[2];
					String semantics = synonyms + " " + gloss;
					String[] semanticUnitArray = new String[2];
					semanticUnitArray[0] = pos;
					semanticUnitArray[1] = semantics;
					semanticsList.add(semanticUnitArray);
				}
			}

			/*
			 * for (String[] semanticUnit : semanticsList) {
			 * System.out.println("POS = " + semanticUnit[0]);
			 * System.out.println("Semantic = " + semanticUnit[1]); }
			 */
		}

	}

}

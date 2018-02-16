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

	public static void main(String[] args) throws JSchException, IOException {

		WordSenseDisambiguator disambiguator = new WordSenseDisambiguator();
		disambiguator.assignPOSTagging();
		disambiguator.extractPOSTagging();
		disambiguator.identifyPolysemyWord();
		disambiguator.readFromWordNet();
		disambiguator.obtainSemanticNet();

	}

	private void readFromWordNet() {
		ReadFromWordnet readWordNet = new ReadFromWordnet();
		readWordNet.readFromWordNet();
		
	}

	private void identifyPolysemyWord() throws IOException {
		List<String> inputWordsList = null;
		BufferedReader brInput = new BufferedReader(new FileReader("sen31"));
		String line = null;
		line = brInput.readLine();
		line = line.replaceAll("[-+.^:,]", "");
		String[] splitWords = line.split(" ");
		inputWordsList = Arrays.asList(splitWords);
		LinkedList<String> linkedList = new LinkedList<String>();
		linkedList.addAll(inputWordsList);
		String polysemyWord = null;
		for (int i = 0; i < linkedList.size(); i++) {
			String word = linkedList.get(i);
			linkedList.remove(i);
			//System.out.println(linkedList);
			for (String otherToken : linkedList) {
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
			linkedList.add(i, word);
		}
		System.out.println("Polysemy word is " + polysemyWord);
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
		Multimap<String, String> word_pos_map = LinkedListMultimap.create();
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
		Collection<Entry<String, String>> entries = word_pos_map.entries();
		entries.forEach(item -> System.out.println(item.getKey() + " :: "
				+ item.getValue()));

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
		List<String[]> semanticsList = new ArrayList<String[]>();
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
					semanticUnitArray[1] = gloss;
					semanticsList.add(semanticUnitArray);
				}
			}

			for (String[] semanticUnit : semanticsList) {
				System.out.println("POS = " + semanticUnit[0]);
				System.out.println("Semantic = " + semanticUnit[1]);
			}
		}

	}

}

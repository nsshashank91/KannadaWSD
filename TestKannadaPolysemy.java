package com.shash.cse;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class TestKannadaPolysemy {

	public static void main(String[] args) {
		String pos11 = "noun";
		String semantic11 = "ವೀರ ಧೀರ ಶೂರ ಬಲವಂತ ಜಗಜಟ್ಟಿ ಸಾಹಸಿ ಧೈರ್ಯಶಾಲಿಯಾದ ಮತ್ತು ದೃಧವಾದ ಮೈಕಟ್ಟನು ಹೊಂದಿದ ಸಾಮಾನ್ಯರಿಗಿಂತ ಹೆಚ್ಚು ಬಲಶಾಲಿಯಾದ ವ್ಯಕ್ತಿ ಟಿಪ್ಪು ಸುಲ್ತಾನನು ಬ್ರಿಟೀಷರೊಂದಿಗೆ ಹೋರಾಡಿದ ವೀರ ಯೋಧ";
		String pos12 = "noun";
		String semantic12 = "ಸೈನಿಕ ಕದನವೀರ ಯೋಧ ಯೋದ್ಧಾ ವೀರ ಶೀಪಾಯಿ ಸಿಪಾಯಿ ಯಾರು ಯುದ್ಧ ಮಾಡುವರೂ ನಿಜವಾದ ಸೈನಿಕ ರಣರಂಗದಲ್ಲಿ ತನ್ನ ಪ್ರಾಣವನ್ನು ನೀಡುವನೆ ವಿನಹ ಬೆನ್ನು ತೋರಿಸಿ ಓಡುವುದಿಲ್ಲ";
		String pos13 = "noun";
		String semantic13 = "ಸೇನೆ ಸೇನ ಸೈನ್ಯ ದಂಡು_ಯೋಧ ದಂಡು_ಯೋಧ ರಕ್ಷಕಪಡೆ ಸಿಪಾಯಿ ವೀರ ಯುದ್ಧದ ಕಾರಣ ಪ್ರಶಿಕ್ಷಿತ ಮತ್ತು ಅಸ್ತ್ರ-ಶಸ್ತ್ರವನ್ನು ಧರಿಸಿಕೊಂಡು ಸೈನಿಕರು ಅಥವಾ ಸಿಪಾಯಿಗಳು ಸಮೂಹಗೊಂಡಿರುವುದು ಭಾರತೀಯ ಸೈನಿಕರು ಶತ್ರುವನ್ನು ಸೋಲಿಸಿಬಿಟ್ಟರು";

		String pos21 = "noun";
		String semantic21 = "ದೇಶ ರಾಷ್ಟ್ರ ಕ್ಷೇತ್ರ ನಾಡು ಸೀಮೆ ಹಲವಾರು ಪ್ರಾಂತ್ಯ, ವಿಭಾಗ, ನಗರ, ಸಂವಿಧಾನಗಳನ್ನೊಳಗೊಂಡ ವಿಶಿಷ್ಟ ಭೂಮಿ ಭಾರತ ನಮ್ಮ ದೇಶ";
		String pos22 = "noun";
		String semantic22 = "ದೇಶ ರಾಷ್ಟ್ರ ನಾಡು ಯಾವುದಾದರು ದೇಶದಲ್ಲಿ ವಾಸಮಾಡುವ ಜನರು ಗಾಂಧೀಜಿಯವರು ಮರಣ ಹೊಂದಿದಾಗ ಇಡೀ ದೇಶದ ಜನತೆ ಕಂಬನಿಮಿಡಿಯಿತು";
		String pos23 = "noun";
		String semantic23 = "ದೇಶ ನಾಡು ರಾಷ್ಟ್ರ ದೇಶ, ಪ್ರದೇಶ, ಜಿಲ್ಲೆ, ಕ್ಷೇತ್ರ, ಪಟ್ಟಣ, ಹಳ್ಳಿ ಮೊದಲಾದವುಗಳಲ್ಲಿ ನೀವು (ಅಥವಾ ಯಾರಾದರೂ ವ್ಯಕ್ತಿ) ಇರುತ್ತೀರಿ ಭಾರತ ನನ್ನ ದೇಶ";

		List<String> semanticBag = new ArrayList<String>();
		semanticBag.add(semantic11);
		semanticBag.add(semantic12);
		semanticBag.add(semantic13);

		
		Set<Integer> semanticBagCount = new LinkedHashSet<Integer>();
		for (int i = 0; i < semanticBag.size(); i++) {
			String semanticList1 = semanticBag.get(i);
			for (int j = i + 1; j < semanticBag.size(); j++) {
				if (j < semanticBag.size()) {
					String semanticList2 = semanticBag.get(j);
					String[] semanticWords1 = semanticList1.split(" ");
					String[] semanticWords2 = semanticList2.split(" ");

					for (int x = 0; x < semanticWords1.length; x++) {
						for (int y = 0; y < semanticWords2.length; y++) {
							String word1 = semanticWords1[x];
							String word2 = semanticWords2[y];
							int length = 0;
							if (word1.length() <= word2.length()) {
								length = word1.length();
							} else {
								length = word2.length();
							}
							int wordMatchLength = 0;
							int z = 0;
							while (z < length) {
								if (word1.charAt(z) == word2.charAt(z)) {
									wordMatchLength++;
									z++;
								} else {
									break;
								}
							}

							if (wordMatchLength >= 3) {
								System.out.println("Matched word is "
										+ word1.substring(0, wordMatchLength));
								System.out.println("Matched sentence is "
										+ semanticBag.get(i));
								System.out.println("Sentence = " + i);
								semanticBagCount.add(i+1);
							}
							//write else block
						}
					}
				}
			}
		}
		int originalSemanticNetSize = semanticBag.size(); 
		int matchingSemanticNetSize = semanticBagCount.size();
		if(matchingSemanticNetSize+1==originalSemanticNetSize){
			System.out.println("All semantics mean same. No polysemy word");
		}
		else{
			Iterator<Integer> iterator = semanticBagCount.iterator();
			int count=0;
			while(iterator.hasNext()){
				count++;
				Integer val = iterator.next();
				if(count!=val){
					System.out.println("Consider sentence "+val);
				}
			}
		}
	}

}

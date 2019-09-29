/*
 * Copyright ©2019 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Spring Quarter 2019 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package marvel;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import graph.Edge;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/** Parser utility to load the Marvel Comics dataset. */
public class MarvelParser<N> {
  private static final String CSV_FILE_PATH = "src/main/resources/marvel/data/marvel.tsv";
  /**
   * Reads the Marvel Universe dataset. Each line of the input file contains a character name and a
   * comic book the character appeared in, separated by a tab character
   *
   * @spec.requires filename is a valid file path
   * @param filename the file that will be read
   * @return a map with data from file
   */
  // TODO: Pick your return type and document it
  public static Map<String, List<String>> parseData(String filename) {
    // TODO: Complete this method
    // Hint: You might want to create a new class to use with the CSV Parser
    Map<String, List<String>> result = new TreeMap<String, List<String>>();
    try {

      Reader reader = Files.newBufferedReader(Paths.get(filename));

      CsvToBean<UserModel> csvToBean = new CsvToBeanBuilder<UserModel>(reader)
              .withType(UserModel.class)
              .withIgnoreLeadingWhiteSpace(true)
              .withSeparator('\t')
              .build();

      Iterator<UserModel> csvUserIterator = csvToBean.iterator();

      while (csvUserIterator.hasNext()) {
        UserModel csvUser = csvUserIterator.next();
        String book = csvUser.getBook();
        String character = csvUser.getHero();
        if (!result.containsKey(book)) {
          result.put(book, new ArrayList<String>());
        }
        result.get(book).add(character);
      }

    }
    catch(FileNotFoundException e) {
      e.printStackTrace();
      System.out.println(filename + ": file not found");
    }
    catch(IOException e) {
      e.printStackTrace();
    }
    return result;
  }
}

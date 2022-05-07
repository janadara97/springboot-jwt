package com.sliit.fyp.util;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

@Component
public class RandomGenerator {

  public String getRandomUserName() {

    String name = "FYP/" + LocalDate.now().getYear() + "/";
    String randomString = "0123456789";

    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 6; i++) {

      int index = (int) (randomString.length()
          * Math.random());

      sb.append(randomString
          .charAt(index));
    }

    return name.concat(sb.toString());

  }
}

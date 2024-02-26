package com.vtxlab.bootcamp.springbootexercise2project.config;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.vtxlab.bootcamp.springbootexercise2project.Service.CryptoGeckoService;

@Component
public class AppRunner implements CommandLineRunner{

  @Autowired
  private CryptoGeckoService cGeckoService;

  @Override
  public void run(String... args) throws Exception {
    cGeckoService.getJPHdataToRedis();
    System.out.println("update time=" + LocalDateTime.now());
  }
}

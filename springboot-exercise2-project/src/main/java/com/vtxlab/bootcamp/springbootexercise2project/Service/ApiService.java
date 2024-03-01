package com.vtxlab.bootcamp.springbootexercise2project.Service;

import java.util.List;

public interface ApiService<T> {

  List<T> fetchData();
  
}

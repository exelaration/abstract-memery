package com.exelaration.abstractmemery.services;

import java.util.ArrayList;
import javax.transaction.Transactional;

@Transactional
public interface UserService {

  public ArrayList<String> getUsersWithText(String text);
}

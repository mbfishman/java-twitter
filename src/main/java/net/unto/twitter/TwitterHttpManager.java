package net.unto.twitter;


interface TwitterHttpManager
{
  void setCredentials(String username, String password);
  
  boolean hasCredentials();
  
  void clearCredentials();
  
  String get(String url)  throws TwitterException;
  
  String get(String url, Parameter[] parameters) throws TwitterException;
  
  String post(String url)  throws TwitterException;
  
  String post(String url, Parameter[] parameters)  throws TwitterException;
}

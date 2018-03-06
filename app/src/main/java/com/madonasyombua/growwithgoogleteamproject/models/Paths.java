package com.madonasyombua.growwithgoogleteamproject.models;

/**
 * Firebase endpoint/nodes
 */

public class Paths {

  public static final String SL = "/";
  static final String USER = "user";
  public static final String CONVERSATION = "conversation";
  public static final String USER_CONVERSATION = "userConversations";
  public static final String MESSAGE = "message";

  public static final String TIME_SENT = "timeSent";
  public static final String PROFILE = "profile";

  class Reply {
    static final String MESSAGE = "message";
    static final String SENDER_ID = "senderId";
    static final String TIME_SENT = "timeSent";
  }

  public class Conversation {
    static final String CREATED = "created";
    public static final String LAST_MESSAGE = "lastMessage";
    public static final String READ = "read";
  }

  public class Profile {
    public static final String FIRST_NAME = "firstName";
  }
}

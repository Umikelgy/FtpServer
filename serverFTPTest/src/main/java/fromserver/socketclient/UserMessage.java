package fromserver.socketclient;

/**
 *@description:
 *
 *@author 10068921(LgyTT)
 *@date 2018/11/27 10:23
 */
public class UserMessage {
    private String name;
    private String passwd;
    private String hostName;
    private int  port;

    public UserMessage(Builder builder) {
        name=builder.name;
        passwd=builder.passwd;
        hostName=builder.hostName;
        port=builder.port;
    }

    public String getName() {
        return name;
    }

    public String getPasswd() {
        return passwd;
    }

    public String getHostName() {
        return hostName;
    }

    public int getPort() {
        return port;
    }
    public static Builder newBuilder(){
        return new Builder();
    }
    public static class Builder{
        String name;
        String passwd;
        String hostName;
        int port;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setPasswd(String passwd) {
            this.passwd = passwd;
            return this;
        }

        public Builder setHostName(String hostName) {
            this.hostName = hostName;
            return this;
        }

        public Builder setPort(int port) {
            this.port = port;
            return this;
        }
        public UserMessage builder(){
            return new UserMessage(this);
        }
    }
}

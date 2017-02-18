package database.diff.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by tomek on 17.02.17.
 */
public class DbCredentials {
    private int port;
    private String user;
    private String pass;
    private String host;
    private String name;

    public int getPort() {
        return port;
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public String getHost() {
        return host;
    }

    public String getName() {
        return name;
    }

    private DbCredentials(Builder builder){
        this.port = builder.port;
        this.user = builder.user;
        this.pass = builder.pass;
        this.host = builder.host;
        this.name = builder.name;
    }

    public static class Builder {
        private int port;
        private String user;
        private String pass;
        private String host;
        private String name;

        public Builder port(int port) {
            this.port = port;
            return this;
        }

        public Builder user(String user) {
            this.user = user;
            return this;
        }

        public Builder pass(String pass) {
            this.pass = pass;
            return this;
        }

        public Builder host(String host) {
            this.host = host;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public DbCredentials build() {
            return new DbCredentials(this);
        }

    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}

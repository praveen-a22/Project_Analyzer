package com.projectanalyzer.dto.ai;

public class AiChatResponse {
    private String response;

    public AiChatResponse() {}

    public AiChatResponse(String response) {
        this.response = response;
    }

    public String getResponse() { return response; }
    public void setResponse(String response) { this.response = response; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String response;

        public Builder response(String response) { this.response = response; return this; }

        public AiChatResponse build() {
            return new AiChatResponse(response);
        }
    }
}

package framework.controller;

import framework.request.enums.HttpMethod;

import java.util.Objects;

public class HttpRequest {
    private String path;
    private HttpMethod type;

    public HttpRequest(String path, HttpMethod type) {
        this.path = path;
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public HttpMethod getType() {
        return type;
    }

    public void setType(HttpMethod type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpRequest that = (HttpRequest) o;
        return Objects.equals(path, that.path) && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, type);
    }
}

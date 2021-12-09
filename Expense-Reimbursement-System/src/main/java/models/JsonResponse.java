package models;

public class JsonResponse {
    Boolean isSuccessful;
    String message;
    Object data;

    public Boolean getSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(Boolean successful) {
        isSuccessful = successful;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "JsonResponse{" +
                "isSuccessful=" + isSuccessful +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}

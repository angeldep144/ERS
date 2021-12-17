package models;

public class JsonResponse {
    Boolean successful;
    Object data;

    public JsonResponse(Boolean successful, Object data) {
        this.successful = successful;
        this.data = data;
    }

    public Boolean getSuccessful() {
        return successful;
    }

    public void setSuccessful(Boolean successful) {
        successful = successful;
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
                "successful=" + successful +
                ", data=" + data +
                '}';
    }
}

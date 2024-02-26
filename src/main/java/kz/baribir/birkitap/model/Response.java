package kz.baribir.birkitap.model;

public class Response {
    private int result_code;
    private String result_msg;
    private Object data;

    public Response(int result_code, String result_msg, Object data) {
        this.result_code = result_code;
        this.result_msg = result_msg;
        this.data = data;
    }

    public Response()
    {

    }

    public static Response create_simple_success()
    {
        return new Response(0,"OK",null);
    }

    public static Response create_simple_success(Object object)
    {
        return new Response(0,"OK",object);
    }

    public static Response create_simple_error()
    {
        return new Response(-1,"Error",null);
    }

    public int getResult_code() {
        return result_code;
    }

    public void setResult_code(int result_code) {
        this.result_code = result_code;
    }

    public String getResult_msg() {
        return result_msg;
    }

    public void setResult_msg(String result_msg) {
        this.result_msg = result_msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "result_code=" + result_code +
                ", result_msg='" + result_msg + '\'' +
                ", data=" + data +
                '}';
    }
}

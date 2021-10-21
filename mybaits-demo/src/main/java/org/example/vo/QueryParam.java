package org.example.vo;

//要传递的参数设置为对象的形式
public class QueryParam {
    private String paramName;
    private Integer paramAge;

    public QueryParam(String paramName, Integer paramAge) {
        this.paramName = paramName;
        this.paramAge = paramAge;
    }

    public QueryParam() {

    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public Integer getParamAge() {
        return paramAge;
    }

    public void setParamAge(Integer paramAge) {
        this.paramAge = paramAge;
    }
}

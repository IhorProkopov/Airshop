package com.epam.prokopov.shop.jsp.custom.taglib;


import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.List;

public class CategoriesHandlerTag extends SimpleTagSupport {


    private static final String CHECKED = "checked";
    private String category;
    private List<String> categories;

    public void doTag() throws JspException, IOException {
        PageContext pageContext = (PageContext) getJspContext();
        JspWriter out = pageContext.getOut();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<label for='");
        stringBuilder.append(category);
        stringBuilder.append("'><input value='true' id='");
        stringBuilder.append(category + "'");
        stringBuilder.append("name = '");
        stringBuilder.append(category + "'");
        stringBuilder.append("type='checkbox'");
        if(categories!=null && categories.contains(category)){
            stringBuilder.append(CHECKED);
        }
        stringBuilder.append(">"+category);
        stringBuilder.append("</label>");
        out.println(stringBuilder.toString());
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getCategories() {
        return categories;
    }

}

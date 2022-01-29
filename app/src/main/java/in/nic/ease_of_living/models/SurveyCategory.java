package in.nic.ease_of_living.models;

/**
 * Created by Neha Jain on 7/13/2017.
 */
public class SurveyCategory {
    private String section_id;
    private String linear_layout,linear_layout_details, image_view,text_view;
    private String str_completed;

    public SurveyCategory(String section_id, String linear_layout, String linear_layout_details, String image_view, String text_view, String str_completed) {
        this.section_id = section_id;
        this.linear_layout = linear_layout;
        this.linear_layout_details = linear_layout_details;
        this.image_view = image_view;
        this.text_view = text_view;
        this.str_completed = str_completed;
    }

    public String getSection_id() {
        return section_id;
    }

    public void setSection_id(String section_id) {
        this.section_id = section_id;
    }

    public String getLinear_layout() {
        return linear_layout;
    }

    public void setLinear_layout(String linear_layout) {
        this.linear_layout = linear_layout;
    }

    public String getImage_view() {
        return image_view;
    }

    public void setImage_view(String image_view) {
        this.image_view = image_view;
    }

    public String getText_view() {
        return text_view;
    }

    public void setText_view(String text_view) {
        this.text_view = text_view;
    }

    public String getStr_completed() {
        return str_completed;
    }

    public void setStr_completed(String str_completed) {
        this.str_completed = str_completed;
    }

    public String getLinear_layout_details() {
        return linear_layout_details;
    }

    public void setLinear_layout_details(String linear_layout_details) {
        this.linear_layout_details = linear_layout_details;
    }
}

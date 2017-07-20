package com.fantasygame.data.model.response;

import java.util.List;

/**
 * Created by HP on 21/06/2017.
 */

public class SportResponse {
    public boolean result;
    public String message;
<<<<<<< HEAD
    public List<Data> data;

    public static class Data {
=======
    public List<Sport> data;

    public class Sport {
>>>>>>> ad8485e904013f72180e461e82a80c8da759f7cd
        public int id;
        public String name;
        public String code;
        public String created_by;
        public String created_at;
        public String updated_by;
        public String updated_at;
<<<<<<< HEAD
        public String image;
=======
>>>>>>> ad8485e904013f72180e461e82a80c8da759f7cd
    }
}

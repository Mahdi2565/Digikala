package ir.mahdidev.digikala.util;

public class Const {
    public class PrefKey{
        public static final String EMAIL_PREF_KEY = "email_pref_key";
        public static final String PREFNAME = "DigikalaPref";
        public static final String PRODUCT_ID_PREF_KEY = "product_id_pref_key";
        public static final String NOTIF_RADIO_BUTTON_ID = "notif_radio_button_id";
        public static final String NOTIF_CUSTOM_EDIT_TEXT = "notif_custom_edit_text";
    }
    public static class IntentKey{
        public static final String PRODUCT_LIST_DATA_INTENT_KEY = "ir.mahdidev.digikala_product_list_data_intnet_key";
        public static final String CATEGORY_POSITION_INTENT_KEY = "ir.mahdidev.digikala_category_position_intent_key";
        public static final String PRODUCT_ID_FORM_NOTIF_TO_PRODUCT_ACTIVITY = "ir.mahdidev.digikala_prdouct_id_from_notif_to_product_activity";
    }
    public static class BundleKey{
        public static final String SUB_CATEGORY_ID = "sub_category_id";
        public static final String PRODUCT_ID_COMMENT = "product_id_comment";
        public static final String PRODUCT_LIST_DATA_BUNDLE_KEY = "product_list_data_bundle_key";
        public static final String CATEGORY_POSITION_BUNDLE_KEY = "category_position_bundle_key";
        public static final String PRODUCT_LIST_DATA_TO_SORT_FRAGMENT_BUNDLE_KEY = "product_data_to_sort_fragment";
        public static final String COMMENT_FRAGMENT_POSITION = "comment_fragment_position";
        public static final String COMMENT_MODEL_BUNDLE_KEY = "comment_model_bundle_key";

    }
    public static class OrderTag{
        public static final String MOST_VISITING_PRODUCT = "popularity";
        public static final String MOST_RATING_PRODUCT = "rating";
        public static final String MOST_NEWEST_PRODUCT = "date";
        public static final String PRICE_PRODUCT = "price";
    }
    public static class RetrofitConst{
        public static final String BASE_URL = "https://woocommerce.maktabsharif.ir/wp-json/wc/v3/";
        public static final String CONSUMER_KEY = "ck_9fc06c2a7292f136b852aceda63740458feb20e1";
        public static final String CONSUMER_SECRET = "cs_5608c8ad5f3ce5b02ac5c629fcad909da759f63a";
        public static final String BASE_URL_GEOCODING_MAP_IR = "https://map.ir/";
        public static final String GEOCODING_MAP_IR_API_KEY = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjNhYmU5MDFlNDVmMzM0ZDdiZmFlN2Y1MTA2OGE3YjY0MTdiMjEyZDM4YzZkYWQ5ZjljYjIzZDBiNWUwMzg1NzA5NDk3NTgyODRhODZhM2FmIn0.eyJhdWQiOiI3MTM1IiwianRpIjoiM2FiZTkwMWU0NWYzMzRkN2JmYWU3ZjUxMDY4YTdiNjQxN2IyMTJkMzhjNmRhZDlmOWNiMjNkMGI1ZTAzODU3MDk0OTc1ODI4NGE4NmEzYWYiLCJpYXQiOjE1NzY1Nzg3MjUsIm5iZiI6MTU3NjU3ODcyNSwiZXhwIjoxNTc5MDg0MzI1LCJzdWIiOiIiLCJzY29wZXMiOlsiYmFzaWMiXX0.RUqq8PtTQYBOUADeEt3P-mWFzMaij2cDJfDzuP5piWnyGON1A-hp-7-W82o0QMj1p99I16TVgmCVHIqhbOvRfvAVT7PWDXssw6XDHjyV9ce2LdtR5aJbxNSySr3l6Bxgz9QouJV4mWu66kZhGR6tRUbEvOy9VhrANb5Tlit51iZQbfzEgRr-Ejz66S0x1YXqMcsa16gDoL8rtkdoSVI-N0Ykp5gDVjFMVhgLnqcfl4d9WtABPAS5SRORE84JcW60XX5Jl2OhPFdPn_f5TYawL81J6j7Hsnu0vos0M-3a3LDKamKHnV3WTluFjNnTIrmSfmMkk4IxMrnVr4jAGFzUPQ";

    }
    public static final String SPLASH_SCREEN_FRAGMENT_TAG = "splash_screen_fragment_tag";
    public static final String MAIN_FRAGMENT_TAG = "main_fragment_tag";
    public static final String NOTIFICATION_CHANNEL_ID = "newest_products";
    public static final String PRIODIC_NEW_PRODUCT_TAG = "priodic_new_product_tag";
    public static final String PERIODIC_NEW_PRODUCT_WORK_MANAGER = "newProductPeriodicWork";
    public static final int DISCOUNT_TAG = 46 ;
    public static final int SPECIAL_TAG = 48 ;
    public static final int FROM_MAIN_FRAGMENT = 0;
    public static final int FROM_PRODUCT_FRAGMENT = 1;
    public static final int FROM_ADDRESS_FRAGMENT = 0;
    public static final int FROM_FINALIZE_BASKET_FRAGMENT = 1;
    public static final int COMMENT_FRAGMENT_EDIT = 0;
    public static final int COMMENT_FRAGMENT_ADD = 1;
}

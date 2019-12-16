package ir.mahdidev.digikala.util;

public class Const {
    public class PrefKey{
        public static final String EMAIL_PREF_KEY = "email_pref_key";
        public static final String PREFNAME = "DigikalaPref";
    }
    public static class IntentKey{
        public static final String PRODUCT_LIST_DATA_INTENT_KEY = "ir.mahdidev.digikala_product_list_data_intnet_key";
        public static final String CATEGORY_POSITION_INTENT_KEY = "ir.mahdidev.digikala_category_position_intent_key";
    }
    public static class BundleKey{
        public static final String SUB_CATEGORY_ID = "sub_category_id";
        public static final String PRODUCT_ID_COMMENT = "product_id_comment";
        public static final String PRODUCT_LIST_DATA_BUNDLE_KEY = "product_list_data_bundle_key";
        public static final String CATEGORY_POSITION_BUNDLE_KEY = "category_position_bundle_key";
        public static final String PRODUCT_LIST_DATA_TO_SORT_FRAGMENT_BUNDLE_KEY = "product_data_to_sort_fragment";
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
    }
    public static final String SPLASH_SCREEN_FRAGMENT_TAG = "splash_screen_fragment_tag";
    public static final String MAIN_FRAGMENT_TAG = "main_fragment_tag";
    public static final String AMAZING_SUGGESTION_ORDER = "amazing_suggestion";
    public static final int DISCOUNT_TAG = 46 ;
    public static final int SPECIAL_TAG = 48 ;
    public static final int FROM_MAIN_FRAGMENT = 0;
    public static final int FROM_PRODUCT_FRAGMENT = 1;
}

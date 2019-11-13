package ir.mahdidev.digikala.util;

public class Const {
    public static class IntentKey{
        public static final String PRODUCT_ID_PRODUCT_ACTIVITY = "product_id_product_activity" ;
    }
    public static class BundleKey{
        public static final String PRODUCT_ID_PRODUCT_FRAGMENT = "product_id_product_fragment";
    }
    public static class OrderTag{
        public static final String MOST_VISITING_PRODUCT = "popularity";
        public static final String MOST_RATING_PRODUCT = "rating";
        public static final String MOST_NEWEST_PRODUCT = "date";
    }
    public static class KeyList{
        public static final String AMAZING_SUGGESTION = "amazingSuggestion";
        public static final String MOST_VISITING_LIST = "mostVisitedProductList";
        public static final String MOST_RATING_LIST = "mostRatingProductList";
        public static final String MOST_NEWEST_LIST = "mostNewestProductList";
        public static final String CATEGORY_LIST = "categoryList";
    }
    public static class RetrofitConst{
        public static final String BASE_URL = "https://woocommerce.maktabsharif.ir/wp-json/wc/v3/";
        public static final String CONSUMER_KEY = "ck_9fc06c2a7292f136b852aceda63740458feb20e1";
        public static final String CONSUMER_SECRET = "cs_5608c8ad5f3ce5b02ac5c629fcad909da759f63a";
    }
    public static final String SPLASH_SCREEN_FRAGMENT_TAG = "splash_screen_fragment_tag";
    public static final String MAIN_FRAGMENT_TAG = "main_fragment_tag";
    public static final String AMAZING_SUGGESTION_ORDER = "amazing_suggestion";
    public static final int DISCOUNT_TAG = 19 ;
    public static final int FROM_MAIN_FRAGMENT = 0;
    public static final int FROM_PRODUCT_FRAGMENT = 1;
}

package ir.mahdidev.digikala.eventbus;

public class OnProductClickedMessage {
    int productId;

    public OnProductClickedMessage(int productId) {
        this.productId = productId;
    }

    public int getProductId() {
        return productId;
    }
}

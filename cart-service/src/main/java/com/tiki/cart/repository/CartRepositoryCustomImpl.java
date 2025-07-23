package com.tiki.cart.repository;

import com.tiki.cart.entity.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepositoryCustom {

    private final MongoTemplate mongoTemplate;



    @Override
    public Optional<Cart> findByProductAndUser(Long productId, String userId) {
        Query query = new Query()
                .addCriteria(Criteria.where("productVariantId").is(productId))
                .addCriteria(Criteria.where("userId").is(userId));
        return Optional.ofNullable(mongoTemplate.findOne(query, Cart.class));
    }

    @Override
    public void updateQuantity(String cartId, int quantity) {
        Query query = new Query(Criteria.where("_id").is(cartId));
        Update update = new Update().set("quantity", quantity);
        mongoTemplate.updateFirst(query, update, Cart.class);
    }

    @Override
    public void updateSelected(String cartId, boolean selected) {
        Query query = new Query(Criteria.where("_id").is(cartId));
        Update update = new Update().set("selected", selected);
        mongoTemplate.updateFirst(query, update, Cart.class);
    }

    @Override
    public void updateSelectedByUserId(String userId, boolean selected) {
        Query query = new Query(Criteria.where("userId").is(userId));
        Update update = new Update().set("selected", selected);
        mongoTemplate.updateMulti(query, update, Cart.class);
    }

    @Override
    public void deleteCart(String cartId) {
        Query query = new Query(Criteria.where("_id").is(cartId));
        mongoTemplate.remove(query, Cart.class);
    }

    @Override
    public void deleteCartByUser(String userId) {
        Query query = new Query(Criteria.where("userId").is(userId));
        mongoTemplate.remove(query, Cart.class);
    }

    @Override
    public List<Cart> findByUserId(String userId) {
        Query query = new Query(Criteria.where("userId").is(userId));
        return mongoTemplate.find(query, Cart.class);
    }
}
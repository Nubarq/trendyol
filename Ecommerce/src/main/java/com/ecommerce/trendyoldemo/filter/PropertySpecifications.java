package com.ecommerce.trendyoldemo.filter;//package com.beauty.salon.filter;
//
//import org.springframework.data.jpa.domain.Specification;
//
//public class PropertySpecifications {
//
//    public static Specification<PropertyEntity> hasRoomCount(Integer roomCount) {
//        return (root, query, cb) ->
//                roomCount == null ? null : cb.equal(root.get("roomCount"), roomCount);
//    }
//
//    public static Specification<PropertyEntity> priceBetween(Integer minPrice, Integer maxPrice) {
//        return (root, query, cb) -> {
//            if (minPrice != null && maxPrice != null) {
//                return cb.between(root.get("price"), minPrice, maxPrice);
//            } else if (minPrice != null) {
//                return cb.greaterThanOrEqualTo(root.get("price"), minPrice);
//            } else if (maxPrice != null) {
//                return cb.lessThanOrEqualTo(root.get("price"), maxPrice);
//            }
//            return null;
//        };
//    }
//
//    public static Specification<PropertyEntity> hasArea(Integer minArea, Integer maxArea) {
//        return (root, query, cb) -> {
//            if (minArea != null && maxArea != null) {
//                return cb.between(root.get("area"), minArea, maxArea);
//            } else if (minArea != null) {
//                return cb.greaterThanOrEqualTo(root.get("area"), minArea);
//            } else if (maxArea != null) {
//                return cb.lessThanOrEqualTo(root.get("area"), maxArea);
//            }
//            return null;
//        };
//    }
//
//    public static Specification<PropertyEntity> hasType(Type type) {
//        return (root, query, cb) ->
//                type == null ? null : cb.equal(root.get("type"), type);
//    }
//
//    public static Specification<PropertyEntity> hasPurchaseType(PurchaseType type) {
//        return (root, query, cb) ->
//                type == null ? null : cb.equal(root.get("purchaseType"), type);
//    }
//}
//

//package com.catJam.Order.order.depr;
//
//public class UpdateMethod {
//        @PutMapping("/update-new/{id}")
//    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order orderDetails) {
//        return orderService.getOrderById(id)
//                .map(order -> {
//                    order.setUserId(orderDetails.getUserId());
//                    order.setOrderDate(orderDetails.getOrderDate());
//                    order.setBookIds(orderDetails.getBookIds());
//                    return ResponseEntity.ok(orderService.saveOrder(order));
//                })
//                .orElse(ResponseEntity.notFound().build());
//    }
//}

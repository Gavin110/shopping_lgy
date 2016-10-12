package com.example.lgy.shopping_lgy.entity;

public class OrderDetail {
	private Integer orderDetailId;//���������
	public OrderDetail(Integer orderDetailId, Integer goodsOrderId, Product product, int goodsNum, double goodsPrice) {
		super();
		this.orderDetailId = orderDetailId;
		this.goodsOrderId = goodsOrderId;
		this.product = product;
		this.goodsNum = goodsNum;
		this.goodsPrice = goodsPrice;
	}
	public Integer getOrderDetailId() {
		return orderDetailId;
	}
	public void setOrderDetailId(Integer orderDetailId) {
		this.orderDetailId = orderDetailId;
	}
	private Integer goodsOrderId;//������id��
	private Product product;//��Ʒ��Ϣ
	private int goodsNum;//��Ʒ����
	private double goodsPrice;

	public OrderDetail(Integer goodsOrderId, Product product, int goodsNum, double goodsPrice) {
		super();
		this.goodsOrderId = goodsOrderId;
		this.product = product;
		this.goodsNum = goodsNum;
		this.goodsPrice = goodsPrice;
	}
	public Integer getGoodsOrderId() {
		return goodsOrderId;
	}
	public void setGoodsOrderId(Integer goodsOrderId) {
		this.goodsOrderId = goodsOrderId;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getGoodsNum() {
		return goodsNum;
	}
	public void setGoodsNum(int goodsNum) {
		this.goodsNum = goodsNum;
	}
	public double getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	

}

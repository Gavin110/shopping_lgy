package com.example.lgy.shopping_lgy.entity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class Order {
	private Integer goodsOrderId;
	private Integer userId;
	private Address address;
	private GoodsOrderState goodsOrderState;//����״̬
	private double goodsTotalPrice;//�����ܼ۸�
	private Timestamp goodsCreateTime;//��������ʱ��
	public Integer getGoodsOrderId() {
		return goodsOrderId;
	}

	public void setGoodsOrderId(Integer goodsOrderId) {
		this.goodsOrderId = goodsOrderId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public GoodsOrderState getGoodsOrderState() {
		return goodsOrderState;
	}

	public void setGoodsOrderState(GoodsOrderState goodsOrderState) {
		this.goodsOrderState = goodsOrderState;
	}

	public double getGoodsTotalPrice() {
		return goodsTotalPrice;
	}

	public void setGoodsTotalPrice(double goodsTotalPrice) {
		this.goodsTotalPrice = goodsTotalPrice;
	}

	public Timestamp getGoodsCreateTime() {
		return goodsCreateTime;
	}

	public void setGoodsCreateTime(Timestamp goodsCreateTime) {
		this.goodsCreateTime = goodsCreateTime;
	}

	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}
	List<OrderDetail> orderDetails;//��Ʒ����
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	
	//orderId,userId,address,orderState,totalPrice,orderCreateTime
	
	public Order(Integer goodsOrderId, Integer userId, Address address, GoodsOrderState goodsOrderState,
			double goodsTotalPrice,Timestamp  goodsCreateTime) {
		super();
		this.goodsOrderId = goodsOrderId;
		this.userId = userId;
		this.address = address;
		this.goodsOrderState = goodsOrderState;
		this.goodsTotalPrice = goodsTotalPrice;
		this.goodsCreateTime = goodsCreateTime;
	}

	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		return super.equals(arg0);
	}
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	

}

package com.webshop.branch.singapore;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.webshop.promotions.BundleDiscount;
import com.webshop.promotions.Voucher;
import com.webshop.tools.DemoPlayer;
import com.webshop.utils.MyCart;
import com.webshop.utils.WebShopItem;

public class DemoPlayerSingapore implements DemoPlayer {

	
	public void play() {
		
		// prepare order list.
		MyCart myCart = new MyCart();
		createOrderList(myCart);
				
		// Set shipping fee
		BigDecimal shippingFee = new BigDecimal("25.00");
				
		// Set tax
		BigDecimal tax = new BigDecimal("0.07");

		// prepare claculator
		ShopCartCalculatorSG cal = new ShopCartCalculatorSG();
		cal.init(createVoucherListSG(), createBundleDiscountList(), shippingFee, tax);
		
				
		// calculator starts
		System.out.println("***************** Welcome to Singapore WebShop *****************\n");
		
		myCart.printOrderList();
		System.out.println("________________________________________________________________\n");
		
		String format = "%-12s = $%-6s";
		BigDecimal subtotal = cal.getSubTotal(myCart);
		System.out.println(String.format(format, "SubTotal", subtotal.toString()));
		System.out.println(String.format(format, "Shipping Fee", shippingFee.toString()));
				
		String vouchersIHave = "voucherCodeSG01_3";
		BigDecimal groundTotal = cal.getGroundTotal(myCart, vouchersIHave);
		System.out.println(String.format(format, "GroundTotal", groundTotal.toString()));
		
		System.out.println("\n*************************** Thank you ***************************\n\n");
		
	} // end play
	
	
	
	// create a shopping list for Singapore branch for demo
	private static void createOrderList(MyCart myCart) {
		myCart.addItem(new WebShopItem("001", "itemSG1", "", new BigDecimal("59.9")), 1);
		myCart.addItem(new WebShopItem("002", "itemSG2", "", new BigDecimal("32.0")), 1);
		myCart.addItem(new WebShopItem("003", "itemSG3", "", new BigDecimal("5.99")), 3);
		myCart.addItem(new WebShopItem("004", "itemSG4", "", new BigDecimal("17.8")), 2);
		myCart.addItem(new WebShopItem("005", "itemSG5", "", new BigDecimal("129.99")), 1);
	}
		
		
		
	// create a voucher list for Singapore branch for demo
	private static List<Voucher> createVoucherListSG() {
		ArrayList<Voucher> vouchers = new ArrayList<Voucher>();
		String voucherKeySG1 = "SG01_";
		String voucherKeySG2 = "SG02_";
		// 15% Discount When Meet $200.00
		vouchers.add( new VoucherSG(voucherKeySG1, createDate("01/01/2015"), new BigDecimal("0.15"), new BigDecimal("200.00"), VoucherSG.VOUCHER_VALUE_PERCENTAGE, true) );
		// instant $20 off
		vouchers.add( new VoucherSG(voucherKeySG2, createDate("31/07/2014"), new BigDecimal("20.00"), new BigDecimal("100.00"), VoucherSG.VOUCHER_VALUE_FIX, true) );
		return vouchers;
	}
	
	
	
	// create a list of bundle for Singapore branch for demo
	private static List<BundleDiscount> createBundleDiscountList() {
		ArrayList<BundleDiscount> bundleDiscounts = new ArrayList<BundleDiscount>();
		// bundle for item1, $59.9 for 1, $100 for 2, deduction =  59.9 * 2 - 100 = $19.8
		bundleDiscounts.add(new BundleDiscount("001", new BigDecimal("19.80"), 2, createDate("31/07/2014")));
		// Expired bundle for item 4, $17.8 for 1, $30 for 2, deduction = 17.8 * 2 - 30 = $5.6
		bundleDiscounts.add(new BundleDiscount("004", new BigDecimal("5.60"), 2, createDate("31/07/2013")));
		// bundle for imte3, $5.99 for 1, $15 for 3, deduction = 5.99 * 3 - 15 = 2.97
		bundleDiscounts.add(new BundleDiscount("003", new BigDecimal("2.97"), 3, createDate("31/12/2014")));
		return bundleDiscounts;
	}
	
	
	
	// Create date with dd/MM/yyyy format
	private static Date createDate(String dateFormat) {
		
		if (dateFormat == null || dateFormat.trim().equals("")) return new Date();
		
		Date resultDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try { resultDate = sdf.parse(dateFormat); }
		catch(Exception e) {
			e.printStackTrace();
			resultDate = new Date(); 
			}
		return resultDate;
	} // end createDate
	
} // end of class
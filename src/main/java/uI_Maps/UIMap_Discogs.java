package uI_Maps;

import org.openqa.selenium.By;

public class UIMap_Discogs {

	public static final By releases = By.cssSelector("div.card_large");
	public static final By buyVinyllBtn = By.cssSelector("a.buy_release_button");
	public static final By buyBtnSection = By.cssSelector("div.marketplace_box_links");
	public static final By table = By.cssSelector("table.table_block");
	public static final By priceColumnItems = By.cssSelector("table.table_block > tbody > tr > td:nth-of-type(5)");
	public static final By priceColumnHeaderTitle = By.cssSelector("table.table_block > thead > tr > th:nth-of-type(3) > a > span");
	public static final By priceColumnHeaderChevron = By.cssSelector("table.table_block > thead > tr > th:nth-of-type(3) > a > i");
	public static final By sellerSortBtn = By.cssSelector("th.seller_info_header a > span");
	public static final By itemsPerPageLimitDropdown = By.cssSelector("select#limit_bottom");
	public static final By nextPageBtn = By.cssSelector("a.pagination_next");
}

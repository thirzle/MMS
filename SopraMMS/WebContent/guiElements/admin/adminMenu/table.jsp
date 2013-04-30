<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" media="print, projection, screen" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-latest.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.tablesorter.js"></script>
<script type="text/javascript">
$(function() {		
	$("#tablesorter-demo").tablesorter({sortList:[[0,0],[2,1]], widgets: ['zebra']});
});	
</script>
	<table id="tablesorter-demo" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
		<thead>
			<tr>
				<th>First Name</th>
				<th>Last Name</th>
				<th>Age</th>
				<th>Total</th>
				<th>Discount</th>
				<th>Difference</th>
				<th>Date</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>Peter</td>
				<td>Parker</td>
				<td>28</td>
				<td>$9.99</td>
				<td>20.9%</td>
				<td>+12.1</td>
				<td>Jul 6, 2006 8:14 AM</td>
			</tr>
			<tr>
				<td>John</td>
				<td>Hood</td>
				<td>33</td>
				<td>$19.99</td>
				<td>25%</td>
				<td>+12</td>
				<td>Dec 10, 2002 5:14 AM</td>
			</tr>
			<tr>
				<td>Clark</td>
				<td>Kent</td>
				<td>18</td>
				<td>$15.89</td>
				<td>44%</td>
				<td>-26</td>
				<td>Jan 12, 2003 11:14 AM</td>
			</tr>
			<tr>
				<td>Bruce</td>
				<td>Almighty</td>
				<td>45</td>
				<td>$153.19</td>
				<td>44.7%</td>
				<td>+77</td>
				<td>Jan 18, 2001 9:12 AM</td>
			</tr>
			<tr>
				<td>Bruce</td>
				<td>Evans</td>
				<td>22</td>
				<td>$13.19</td>
				<td>11%</td>
				<td>-100.9</td>
				<td>Jan 18, 2007 9:12 AM</td>
			</tr>
			<tr>
				<td>Bruce</td>
				<td>Evans</td>
				<td>22</td>
				<td>$13.19</td>
				<td>11%</td>
				<td>0</td>
				<td>Jan 18, 2007 9:12 AM</td>
			</tr>
		</tbody>
	</table>


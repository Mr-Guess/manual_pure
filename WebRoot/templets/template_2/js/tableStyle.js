/*
¸ôÐÐ»»É«
*/
function Grid(id,titlecss,css1,css2)
{
	var test = document.getElementById(id);
	if(test != null)
	{
		var testlength = test.rows.length;
		test.rows[0].className=titlecss;
		for(var i=1;i<testlength;i++)
		{
			if(i%2 == 0)
			{
				for(var j = 0;j<test.rows[i].cells.length;j++)
				test.rows[i].cells[j].className = css1;
			}
			else
			{
				for(var j = 0;j<test.rows[i].cells.length;j++)
				test.rows[i].cells[j].className = css2;
			}
		}
	}
}
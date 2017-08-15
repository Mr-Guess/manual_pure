<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:template match="/">
		<ul class="menu">
			<xsl:apply-templates select="/root/item"/>
		</ul>
	</xsl:template>
	<xsl:template match="item">
		<li id="father" class="nochose">
			<xsl:attribute name="onmousemove">
				if(this.className=='nochose'){
					this.className='mousemove';
				}
			</xsl:attribute>
			<xsl:attribute name="onmouseout">
				if(this.className=='mousemove'){
					this.className='nochose';
				}
			</xsl:attribute>
			<xsl:attribute name="onclick">
			<!-- firefox识别parentNode -->
				refreshmenu(this.parentElement||this.parentNode);
				this.className=(this.className=='chose'?'nochose':'chose');
				<xsl:if test="count(item)!=0">openItem(<xsl:value-of select="position()"/>);</xsl:if>
			</xsl:attribute>
			<a onclick="{@onclick}">
				<xsl:if test="@href!=''">
					<xsl:attribute name="href"><xsl:value-of select="@href"/></xsl:attribute>
					<xsl:attribute name="target"><xsl:value-of select="@target"/></xsl:attribute>
				</xsl:if>
				<div>
					<xsl:value-of select="@remark"/>
				</div>
			</a>
		</li>
	</xsl:template>
</xsl:stylesheet>

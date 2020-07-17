<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : cake-baking-a-moment.xsl
    Created on : July 5, 2020, 4:12 PM
    Author     : duong
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/">
        <cake>
            <id>0</id>
            <name>
                <xsl:value-of select="//h1[@class='post-title']/text()"/>
            </name>
            <description>
                <xsl:value-of select="//div[@class='post-content']/h2/text()"/>
            </description>
            <image>https://bakingamoment.com/wp-content/uploads/2019/01/IMG_2714-best-german-chocolate-cake-recipe-720x720.jpg</image>
            <categoryid>0</categoryid>
            <link>https://bakingamoment.com</link>
            <time>Not yet</time>
            <xsl:choose>
                <xsl:when test="//*[@id='wprm-tooltip-1594870014252']">
                    <xsl:value-of select="//*[@id='wprm-tooltip-1594870014252']/text()"/>
                </xsl:when>
                <xsl:otherwise>
                    <serves>10</serves>
                </xsl:otherwise>
            </xsl:choose>
            <views>0</views>
        </cake>
    </xsl:template>

</xsl:stylesheet>

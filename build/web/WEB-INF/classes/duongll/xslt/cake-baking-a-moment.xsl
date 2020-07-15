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
            <image>
                <xsl:value-of select="//*[@id='wprm-recipe-container-83462']/div/div[1]/div[2]/img"/>
            </image>
            <categoryid>0</categoryid>
            <link>https://bakingamoment.com</link>
            <time>Not yet</time>
            <serves>10</serves>
            <views>0</views>
        </cake>
    </xsl:template>

</xsl:stylesheet>

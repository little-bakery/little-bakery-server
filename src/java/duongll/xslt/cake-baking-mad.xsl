<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : cake.xsl
    Created on : June 27, 2020, 4:28 PM
    Author     : duong
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml"/>
    <xsl:template match="/">
        <xsl:variable name="vUpper" select="'ABCDEFGHIJKLMNOPQRSTUVWXYZ'"/>
        <xsl:variable name="vLower" select="'abcdefghijklmnopqrstuvwxyz'"/>
        <xsl:variable name="vAlpha" select="concat($vUpper, $vLower)"/>
        <xsl:variable name="serves" select="normalize-space(//span[@itemprop='recipeYield']/text())"/>
        <cake>
            <id>0</id>
            <name>
                <xsl:value-of select="//h1[@class='h2' and @itemprop='name']"/>
            </name>
            <description>
                <xsl:value-of 
                    select="concat(//div[@class='free-content intro' and @itemprop='description']//p/text(), //div[@class='free-content intro' and @itemprop='description']//a/text())"/>
            </description>
            <image>
                <xsl:value-of select="//div[@class='img-slider__img ri is-loaded']/@data-src"/>
            </image>
            <categoryid>0</categoryid>
            <link>https://www.bakingmad.com</link>
            <time>
                <xsl:value-of select="translate(//meta[@itemprop='totalTime']/@content, 'PT', '')"/>
            </time>
            <serves>
                <xsl:value-of select="translate($serves, $vAlpha, '')" />
            </serves>
        </cake>
    </xsl:template>

</xsl:stylesheet>

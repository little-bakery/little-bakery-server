<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : ingredient.xsl
    Created on : June 27, 2020, 10:13 PM
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
        <!--<xsl:variable name="countIn" select="count(//h3[@class='sidebar-block__list-heading'])"/>-->       
        <!--        <ingredients>
            <xsl:choose>
                <xsl:when test="string(//h3[@class='sidebar-block__list-heading']) != ''">
                    <xsl:for-each select="//h3[@class='sidebar-block__list-heading']">
                        <ingredient>
                            <id>0</id>
                            <name>
                                <xsl:value-of select="./text()" />
                            </name>
                            <cakeid>0</cakeid>
                        </ingredient>
                    </xsl:for-each>
                </xsl:when>
                <xsl:otherwise>
                    <ingredient>
                        <id>0</id>
                        <name>No Ingredient</name>
                        <cakeid>0</cakeid>
                    </ingredient>
                </xsl:otherwise>
            </xsl:choose> 
        </ingredients>-->
        <ingredients>
            <xsl:for-each select="//div[@class='sidebar']//section//div[@class='sidebar-block__content']/ul">                
                <xsl:variable name="count" select="position()" />
                <ingredient>
                    <id>
                        <xsl:value-of select="position()" />
                    </id>
                    <xsl:choose>
                        <xsl:when test="(//h3[@class='sidebar-block__list-heading'])[position()]">
                            <name>
                                <xsl:value-of select="(//h3[@class='sidebar-block__list-heading'])[$count]/text()"/>
                            </name>
                        </xsl:when>
                        <xsl:otherwise>
                            <name>For the Food</name>
                        </xsl:otherwise>                    
                    </xsl:choose>
                    <cakeid>0</cakeid>
                </ingredient>
            </xsl:for-each>
        </ingredients>            
    </xsl:template>

</xsl:stylesheet>

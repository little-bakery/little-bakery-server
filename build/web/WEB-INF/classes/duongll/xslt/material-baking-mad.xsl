<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : material.xsl
    Created on : June 27, 2020, 10:14 PM
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
        <xsl:variable name="vUpper" select="'ABCDEFGHIJKLMNOPQRSTUVWXYZÂ'"/>
        <xsl:variable name="vLower" select="'abcdefghijklmnopqrstuvwxyzâ'"/>
        <xsl:variable name="vAlpha" select="concat($vUpper, $vLower)"/>
        <materials>
            <xsl:for-each select="//section[@class='sidebar-block recipe-ingredients']/div[@class='sidebar-block__content']/ul">
                <xsl:variable name="count" select="position()" />                                
                <xsl:for-each select="./li[@itemprop='recipeIngredient'][position()]">
                    <xsl:variable name="quantity" select="translate(./span/text(), $vUpper, $vLower)" />
                    <xsl:variable name="unit" select="./span/text()" />
                    <material>
                        <id>0</id>
                        <name>
                            <xsl:value-of select="./span[@class='ing']/span/text()" />
                        </name>
                        <unit>
                            <xsl:value-of select="./span/text()"/>
                            <!--<xsl:choose>
                            <xsl:when test="$unit != ''">-->
                            <!--<xsl:variable name="u" select="translate($unit, translate($unit, $vAlpha, ''), '')"/>-->
                            <!--<xsl:choose>
                                <xsl:when test="$u = 'teaspoon'">tsp</xsl:when>
                                <xsl:when test="$u = 'tsp'">tsp</xsl:when>
                                <xsl:when test="$u = 'tablespoon'">tbsp</xsl:when>
                                <xsl:when test="$u = 'tbl'">tbsp</xsl:when>
                                <xsl:when test="$u = 'tbl.sp'">tbsp</xsl:when>
                                <xsl:when test="$u = 'tbsp'">tbsp</xsl:when>
                                <xsl:when test="$u = 'ounce'">oz</xsl:when>
                                <xsl:when test="$u = 'oz'">oz</xsl:when>
                                <xsl:when test="$u = 'fluid ounce'">fl oz</xsl:when>
                                <xsl:when test="$u = 'fl oz'">fl oz</xsl:when>
                                <xsl:when test="$u = 'oz fl'">fl oz</xsl:when>
                                <xsl:when test="$u = 'oz.lf'">fl oz</xsl:when>
                                <xsl:when test="$u = 'floz'">fl oz</xsl:when>
                                <xsl:when test="$u = 'fl.oz'">fl oz</xsl:when>
                                <xsl:when test="$u = 'pound'">lb</xsl:when>
                                <xsl:when test="$u = 'lb'">b</xsl:when>
                                <xsl:when test="$u = 'gram'">g</xsl:when>
                                <xsl:when test="$u = 'g'">g</xsl:when>
                                <xsl:when test="$u = 'kilogram'">kg</xsl:when>
                                <xsl:when test="$u = 'kg'">kg</xsl:when>
                                <xsl:when test="$u = 'milliliter'">ml</xsl:when>
                                <xsl:when test="$u = 'ml'">ml</xsl:when>
                                <xsl:when test="$u = 'fahrenheit'">F</xsl:when>
                                <xsl:when test="$u = 'f'">F</xsl:when>
                                <xsl:when test="$u = 'celsius'">C</xsl:when>
                                <xsl:when test="$u = 'c'">C</xsl:when>
                            </xsl:choose>-->
                            <!--</xsl:when>
                                <xsl:when test="$unit = ''">
                                    <xsl:text>No Quantity</xsl:text>
                                </xsl:when>
                            </xsl:choose>-->
                        </unit>
                        <!--<xsl:choose>
                        <xsl:when test="$quantity != ''">
                            <xsl:value-of select="normalize-space(translate($quantity, $vAlpha, ''))"/>
                        </xsl:when>
                        <xsl:when test="$quantity = ''">-->                           
                        <!--</xsl:when>
                        </xsl:choose>-->
                        <xsl:choose>
                            <!--<xsl:when test="//h3[@class='sidebar-block__list-heading']">-->
                            <xsl:when test="(//h3[@class='sidebar-block__list-heading'])[$count]">
                                <ingredientid>
                                    <id>0</id>
                                    <name>
                                        <xsl:value-of select="//h3[@class='sidebar-block__list-heading'][$count]/text()" />
                                    </name>
                                    <cakeid>0</cakeid>
                                </ingredientid>
                            </xsl:when>
                            <xsl:otherwise>
                                <ingredientid>
                                    <id>0</id>
                                    <name>For the Food</name>
                                    <cakeid>0</cakeid>
                                </ingredientid>
                            </xsl:otherwise>
                        </xsl:choose> 
                        
                    </material>
                </xsl:for-each>
            </xsl:for-each>
        </materials>
    </xsl:template>

</xsl:stylesheet>

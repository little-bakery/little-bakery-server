<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : material-baking-a-moment.xsl
    Created on : July 5, 2020, 4:13 PM
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
        <materials>
            <xsl:for-each select="
                        //article[@class='post single-post-content']
                        //div[@class='post-content']
                        //div[@class='wprm-recipe-container']
                        //div[@class='wprm-recipe wprm-recipe-tastefully-simple']
                        /div[@class='wprm-recipe-ingredients-container']
                        /div[@class='wprm-recipe-ingredient-group']">
                <xsl:variable name="count" select="position()" />
                <xsl:for-each select="//article[@class='post single-post-content']                     
                                    //div[@class='post-content']                     
                                    //div[@class='wprm-recipe-container']                     
                                    //div[@class='wprm-recipe wprm-recipe-tastefully-simple']                     
                                    /div[@class='wprm-recipe-ingredients-container']/div[@class='wprm-recipe-ingredient-group'][$count]/ul/li">
                    <material>
                        <id>0</id>
                        <name>
                            <xsl:choose>
                                <xsl:when test="span[@class='wprm-recipe-ingredient-name']/a">
                                    <xsl:value-of select="span[@class='wprm-recipe-ingredient-name']/a/text()"/>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:value-of select="span[@class='wprm-recipe-ingredient-name']/text()"/>
                                </xsl:otherwise>
                            </xsl:choose>
                        </name>
                        <unit>
                            <xsl:choose>
                                <xsl:when test="span[@class='wprm-recipe-ingredient-amount'] and span[@class='wprm-recipe-ingredient-unit']">
                                    <xsl:value-of select="concat(span[@class='wprm-recipe-ingredient-amount']/text(), ' ', span[@class='wprm-recipe-ingredient-unit']/text())"/>
                                </xsl:when>
                                <xsl:when test="not(span[@class='wprm-recipe-ingredient-amount']) and span[@class='wprm-recipe-ingredient-unit']">
                                    <xsl:value-of select="span[@class='wprm-recipe-ingredient-unit']/text()"/>
                                </xsl:when>
                                <xsl:when test="span[@class='wprm-recipe-ingredient-amount'] and not(span[@class='wprm-recipe-ingredient-unit'])">
                                    <xsl:value-of select="span[@class='wprm-recipe-ingredient-amount']/text()"/>
                                </xsl:when>
                            </xsl:choose>
                        </unit>
                        <ingredientid>
                            <id>0</id>
                            <xsl:choose>
                                <xsl:when test="//article[@class='post single-post-content']
                                                //div[@class='post-content']
                                                //div[@class='wprm-recipe-container']
                                                //div[@class='wprm-recipe wprm-recipe-tastefully-simple']
                                                /div[@class='wprm-recipe-ingredients-container']
                                                /div[@class='wprm-recipe-ingredient-group'][$count]/div">
                                    <name>
                                        <xsl:value-of select="//article[@class='post single-post-content']
                                                //div[@class='post-content']
                                                //div[@class='wprm-recipe-container']
                                                //div[@class='wprm-recipe wprm-recipe-tastefully-simple']
                                                /div[@class='wprm-recipe-ingredients-container']
                                                /div[@class='wprm-recipe-ingredient-group'][$count]/div/text()"/>
                                    </name>
                                </xsl:when>
                                <xsl:otherwise>
                                    <name>For the Food</name>
                                </xsl:otherwise>
                            </xsl:choose>                            
                            <cakeid>0</cakeid>
                        </ingredientid>
                    </material>
                </xsl:for-each>
            </xsl:for-each>
        </materials>
    </xsl:template>

</xsl:stylesheet>

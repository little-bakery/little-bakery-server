<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : ingredient-baking-a-moment.xsl
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
        <ingredients>
            <xsl:for-each select="
                    //article[@class='post single-post-content']
                    //div[@class='post-content']
                    //div[@class='wprm-recipe-container']
                    //div[@class='wprm-recipe wprm-recipe-tastefully-simple']
                    /div[@class='wprm-recipe-ingredients-container']
                    /div[@class='wprm-recipe-ingredient-group']" >
                <xsl:variable name="count" select="position()" />
                <ingredient>
                    <id>0</id>
                    <xsl:choose>
                        <xsl:when test="
                                //article[@class='post single-post-content']
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
                </ingredient>
            </xsl:for-each>
        </ingredients>
    </xsl:template>

</xsl:stylesheet>

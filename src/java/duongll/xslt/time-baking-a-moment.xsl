<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : time-baking-a-moment.xsl
    Created on : July 5, 2020, 9:07 PM
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
        <times>
            <xsl:variable name="hour-prepare"
                          select="//span[@class='wprm-recipe-details wprm-recipe-details-hours wprm-recipe-prep_time wprm-recipe-prep_time-hours']/text()" />
            <xsl:variable name="minute-prepare"
                          select="//span[@class='wprm-recipe-details wprm-recipe-details-minutes wprm-recipe-prep_time wprm-recipe-prep_time-minutes']/text()" />
            <xsl:variable name="hour-cook"
                          select="//span[@class='wprm-recipe-details wprm-recipe-details-hours wprm-recipe-cook_time wprm-recipe-cook_time-hours']/text()" />
            <xsl:variable name="minute-cook"
                          select="//span[@class='wprm-recipe-details wprm-recipe-details-minutes wprm-recipe-cook_time wprm-recipe-cook_time-minutes']/text()" />
            <prepare>
                <xsl:choose>
                    <xsl:when test="$hour-prepare and $minute-prepare">
                        <xsl:value-of select="concat($hour-prepare, 'H', $minute-prepare, 'M')"/>
                    </xsl:when>
                    <xsl:when test="not($hour-prepare) and $minute-prepare">
                        <xsl:value-of select="concat(0, 'H', $minute-prepare, 'M')"/>
                    </xsl:when>
                    <xsl:when test="$hour-prepare and not($minute-prepare)">
                        <xsl:value-of select="concat($hour-prepare, 'H', 0, 'M')"/>
                    </xsl:when>
                    <xsl:otherwise>No Time</xsl:otherwise>
                </xsl:choose>
            </prepare>
            <cook>
                <xsl:choose>
                    <xsl:when test="$hour-cook and $minute-cook">
                        <xsl:value-of select="concat($hour-cook, 'H', $minute-cook, 'M')"/>
                    </xsl:when>
                    <xsl:when test="not($hour-cook) and $minute-cook">
                        <xsl:value-of select="concat(0, 'H', $minute-cook, 'M')"/>
                    </xsl:when>
                    <xsl:when test="$hour-cook and not($minute-cook)">
                        <xsl:value-of select="concat($hour-cook, 'H', 0, 'M')"/>
                    </xsl:when>
                    <xsl:otherwise>No Time</xsl:otherwise>
                </xsl:choose>
            </cook>
        </times>
    </xsl:template>

</xsl:stylesheet>

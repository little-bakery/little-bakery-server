<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : preparation-baking-a-moment.xsl
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
        <cakepreparations>
            <xsl:for-each select="(//div[@class='wprm-recipe-instructions-container'])[1]/div[@class='wprm-recipe-instruction-group'][1]/ol/li">
                <cakepreparation>
                    <id>0</id>
                    <contentprepare>
                        <xsl:value-of select="(//div[@class='wprm-recipe-instructions-container'])[1]/div[@class='wprm-recipe-instruction-group'][1]/ol/li/div/p/text()" />
                    </contentprepare>
                    <orderprepare>
                        <xsl:value-of select="position()"/>
                    </orderprepare>
                    <cakeid>0</cakeid>
                </cakepreparation>
            </xsl:for-each>
        </cakepreparations>
    </xsl:template>

</xsl:stylesheet>

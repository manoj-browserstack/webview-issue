/*
 * Copyright (C) 2024. by flatexDEGIRO AG, Frankfurt (Main), Germany. All Rights Reserved.
 */

package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Node {
    private List<String> className;
    private List<String> notClassName;
    private String containsText;
    private Map<String, String> data = new HashMap<>();
    private Map<String, String> attr = new HashMap<>();
    private String id;
    private Integer line;
    private String name;
    private Integer nthChild;
    private Integer nthOfType;
    private String placeholder;
    private String role;
    private String tagName;
    private String text;
    private String textStartsWith;
    private String type;
    private String value;

    public Node addClassName( String className ) {
        if( this.className == null )
            className( className );
        else
            this.className.add( className );
        return this;
    }

    public Node addNotClassName( String className ) {
        if( this.notClassName == null )
            notClassName( className );
        else
            this.notClassName.add( className );
        return this;
    }

    public Node className( String className ) {
        this.className = new ArrayList<>();
        this.className.add( className );
        return this;
    }

    public Node notClassName( String className ) {
        this.notClassName = new ArrayList<>();
        this.notClassName.add( className );
        return this;
    }

    public Node containsText( String containsText ) {
        this.containsText = containsText;
        return this;
    }

    public Node data( String dataTitle, String value) {
        data.put( dataTitle, value );
        return this;
    }

    public Node attr( String attributeName, String attributeValue) {
        attr.put( attributeName, attributeValue );
        return this;
    }

    public Node id( String id ) {
        this.id = id;
        return this;
    }

    public Node line( int line ) {
        this.line = line;
        return this;
    }

    public Node name( String name ) {
        this.name = name;
        return this;
    }

    public Node nthChild( int nthChild ) {
        this.nthChild = nthChild;
        return this;
    }

    public Node nthOfType( int nthOfType ) {
        this.nthOfType = nthOfType;
        return this;
    }

    public Node placeHolder( String placeholder ) {
        this.placeholder = placeholder;
        return this;
    }

    public Node role( String role ) {
        this.role = role;
        return this;
    }

    public Node tagName( String tagName ) {
        this.tagName = tagName;
        return this;
    }

    public Node text( String text ) {
        this.text = text;
        return this;
    }

    public Node textStartsWith( String textStartsWith ) {
        this.textStartsWith = textStartsWith;
        return this;
    }

    public Node type( String type ) {
        this.type = type;
        return this;
    }

    public Node value( String value ) {
        this.value = value;
        return this;
    }

    /*
     * tags
     */
    public Node div() {
        this.tagName = "div";
        return this;
    }

    public Node span() {
        this.tagName = "span";
        return this;
    }

    public Node input() {
        this.tagName = "input";
        return this;
    }

    public Node button() {
        this.tagName = "input";
        this.type = "button";
        return this;
    }

    public Node file() {
        this.tagName = "input";
        this.type = "file";
        return this;
    }

    public Node label() {
        this.tagName = "label";
        return this;
    }

    public Node tr() {
        this.tagName = "tr";
        return this;
    }

    public Node th() {
        this.tagName = "th";
        return this;
    }

    public Node td() {
        this.tagName = "td";
        return this;
    }

    public String toString() {
        StringBuilder xpath = new StringBuilder();

        if( tagName == null )
            xpath.append( "//*" );
        else
            xpath.append( "//" ).append( tagName );

        if( nthChild != null )
            xpath.append( "[" ).append(nthChild).append( "]" );

        if( id != null ) {
            xpath.append( "[@id='" ).append( id );
            xpath.append( "' or @resource-id='" ).append( id );
            xpath.append( "' or @resource-id='de.xcom.flatexde:id/" ).append( id );
            xpath.append( "' or @resource-id='de.xcom.flatexat:id/" ).append( id );
            xpath.append( "' or name='" ).append( id ).append( "']" );
        }

        if( name != null ) {
            xpath.append( "[name='" ).append( name ).append( "']" );
        }

        if( className != null ) {
            for( String item:className ) {
                xpath.append( "[contains(@class,' " ).append( item ).append( "') or");
                xpath.append( " contains(@class,'" ).append( item ).append( " ') or");
                xpath.append( " @class='" ).append( item ).append( "']" );
            }
        }

        if( notClassName != null ) {
            for( String item:notClassName ) {
                xpath.append( "[not(contains(@class,' " ).append( item ).append( "') or");
                xpath.append( " contains(@class,'" ).append( item ).append( " ') or");
                xpath.append( " @class='" ).append( item ).append( "')]" );
            }
        }

        if( placeholder != null )
            xpath.append( "[@placeholder='").append( placeholder ).append( "']" );

        if( value != null )
            xpath.append( "[@value='").append( value ).append( "']" );

        if( text != null ) {
            xpath.append( "[@text='" ).append( text );
            xpath.append( "' or text()='" ).append( text );
            xpath.append( "' or normalize-space(text())='" ).append( text ).append( "']" );
        }

        if( containsText != null ) {
            xpath.append( "[contains(normalize-space(text()");
            if( line != null )
                xpath.append( "[" ).append( line ).append( "]" );
            xpath.append("),'" ).append( containsText ).append( "')]" );
        }

        if( textStartsWith != null )
            xpath.append( "[starts-with(text(), '").append( textStartsWith ).append( "')]" );

        if( type != null )
            xpath.append( "[@type='" ).append( type ).append( "']" );

        if( role != null )
            xpath.append( "[@role='" ).append( role ).append( "']" );

        for( String dataTitle:data.keySet() )
        {
            xpath.append( "[@data-" ).append( dataTitle ).append( "='" ).append( data.get( dataTitle ) ).append( "']" );
        }

        for( String attributeName: attr.keySet() )
        {
            xpath.append( "[@" ).append( attributeName ).append( "='" ).append( attr.get( attributeName ) ).append( "']" );
        }

        if( nthOfType != null )
            xpath.append("[").append(nthOfType).append("]");

        return xpath.toString();
    }
}

/*
 * Copyright (c) 2006 Ola Bini
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of 
 * this software and associated documentation files (the "Software"), to deal in 
 * the Software without restriction, including without limitation the rights to 
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies 
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
 * SOFTWARE.
 */
/**
 * $Id: YAMLDumpTest.java,v 1.2 2006/09/24 16:32:35 olabini Exp $
 */
package org.jvyaml;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:ola.bini@ki.se">Ola Bini</a>
 * @version $Revision: 1.2 $
 */
public class YAMLDumpTest extends TestCase {
    public YAMLDumpTest(final String name) {
        super(name);
    }

    public void testBasicStringDump() {
        assertEquals("--- str\n", YAML.dump("str"));
    }

    public void testBasicHashDump() {
        Map ex = new HashMap();
        ex.put("a","b");
        assertEquals("--- \na: b\n", YAML.dump(ex));
    }

    public void testBasicListDump() {
        List ex = new ArrayList();
        ex.add("a");
        ex.add("b");
        ex.add("c");
        assertEquals("--- \n- a\n- b\n- c\n", YAML.dump(ex));
    }

    public void testVersionDumps() {
        assertEquals("--- !!int 1\n", YAML.dump(new Integer(1),YAML.config().explicitTypes(true)));
        assertEquals("--- !int 1\n", YAML.dump(new Integer(1),YAML.config().version("1.0").explicitTypes(true)));
    }

    public void testMoreScalars() {
        assertEquals("--- !!str 1.0\n", YAML.dump("1.0"));
    }

    public void testDumpJavaBean() {
        final java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.clear();
        cal.set(1982,5-1,3); // Java's months are zero-based...
        
        final TestBean toDump = new TestBean("Ola Bini", 24, cal.getTime());
        assertEquals("--- !java/object:org.jvyaml.TestBean\nname: Ola Bini\nage: 24\nborn: 1982-05-03 00:00:00 +02:00\n", YAML.dump(toDump));
       
    }
}// YAMLDumpTest

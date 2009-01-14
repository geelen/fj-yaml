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
 * $Id: TestBean.java,v 1.1 2006/09/24 16:32:58 olabini Exp $
 */
package org.jvyaml;

import java.util.Date;

/**
 * @author <a href="mailto:ola.bini@ki.se">Ola Bini</a>
 * @version $Revision: 1.1 $
 */
public class TestBean {
    private String name;
    private int age;
    private Date born;

    public TestBean() {
    }

    public TestBean(final String name, final int age, final Date born) {
        this.name = name;
        this.age = age;
        this.born = born;
    }
    
    public String getName() {
        return this.name;
    }

    public int getAge() {
        return age;
    }

    public Date getBorn() {
        return born;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setAge(final int age) {
        this.age = age;
    }

    public void setBorn(final Date born) {
        this.born = born;
    }

    public boolean equals(final Object other) {
        boolean ret = this == other;
        if(!ret && other instanceof TestBean) {
            TestBean o = (TestBean)other;
            ret = 
                this.name == null ? o.name == null : this.name.equals(o.name) &&
                this.age == o.age &&
                this.born == null ? o.born == null : this.born.equals(o.born);
        }
        return ret;
    }

    public int hashCode() {
        int val = 3;
        val += 3 * (name == null ? 0 : name.hashCode());
        val += 3 * age;
        val += 3 * (born == null ? 0 : born.hashCode());
        return val;
    }

    public String toString() {
        return "#<org.jvyaml.TestBean name=\"" + name + "\" age=" + age + " born=\"" + born + "\">";
    }
}// TestBean

/*
 * [The "BSD licence"]
 * Copyright (c) 2013 Dandelion
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * 3. Neither the name of Dandelion nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.github.dandelion.datatables.integration.html;

import static org.fest.assertions.Assertions.assertThat;

import java.io.IOException;

import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.github.dandelion.datatables.integration.ThymeleafContextRunner;
import com.github.dandelion.datatables.testing.BaseIT;
import com.github.dandelion.datatables.testing.utils.ThymeleafTest;

/**
 * Test the HTML markup generation.
 *
 * @author Thibault Duchateau
 */
@RunWith(ThymeleafContextRunner.class)
@ThymeleafTest
public class DomSourceIT extends BaseIT {

	@Test
	public void should_generate_table_markup() throws IOException, Exception {
		goToPage("html/default");
		
		assertThat(getTable()).hasSize(1);
		assertThat(getTable().find("thead")).hasSize(1);
		assertThat(getTable().find("tbody")).hasSize(1);
		
		// By default, paging is set to 10
		assertThat(getTable().find("tbody").find("tr")).hasSize(10);
		
		// Let's look at the cells in the second tr
		assertThat(getTable().find("tbody").find("tr", 1).find("td", 0).getText()).isEqualTo("2");
		assertThat(getTable().find("tbody").find("tr", 1).find("td", 1).getText()).isEqualTo("Vanna");
		assertThat(getTable().find("tbody").find("tr", 1).find("td", 2).getText()).isEqualTo("Salas");
		assertThat(getTable().find("tbody").find("tr", 1).find("td", 3).getText()).isEqualTo("Denny");
		assertThat(getTable().find("tbody").find("tr", 1).find("td", 4).getText()).isEqualTo("bibendum.fermentum.metus@ante.ca");
	}

	@Test
	public void should_generate_script_tag() {
		goToPage("html/default");
		FluentWebElement body = findFirst("body");
		assertThat(body.find("script")).hasSize(1);
	}
	
	@Test
	public void should_render_empty_cell() throws IOException, Exception {
		goToPage("html/default");

		// I know that the 4th cell of the first row must be empty (City is null in the data source)
		assertThat(getTable().find("tbody").findFirst("tr").find("td", 3).getText()).isEqualTo("");
	}
	
	@Test
	public void should_render_default_value_in_cell() throws IOException, Exception {
		goToPage("html/default_values");

		// I know that the 4th cell of the first row is empty but is filled with a default value
		assertThat(getTable().find("tbody").findFirst("tr").find("td", 3).getText()).isEqualTo("Default value");
	}
	
	@Test
	public void when_emptylist_should_use_static_template() {
		goToPage("html/empty_collection");
		assertThat(getTable().find("tbody").find("tr")).hasSize(1);
	}
	
	@Test
	public void when_nulllist_should_use_static_template() {
		goToPage("html/null_collection");
		assertThat(getTable().find("tbody").find("tr")).hasSize(1);
	}
}

/*
 * Copyright (c) 2016 Haulmont
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.haulmont.timesheets.web.commandline;

import com.haulmont.cuba.gui.components.autocomplete.Suggester;
import com.haulmont.cuba.web.gui.components.WebSourceCodeEditor;
import com.haulmont.timesheets.gui.commandline.CommandLine;

/**
 * @author degtyarjov
 */
public class WebCommandLine extends WebSourceCodeEditor implements CommandLine {
    @Override
    public void setSuggester(Suggester suggester) {
        this.suggester = suggester;

        if (suggester != null && suggestionExtension == null) {
            suggestionExtension = new CommandLineSuggestionExtension(new CommandLineSourceCodeEditorSuggester());
            suggestionExtension.extend(component);
            suggestionExtension.setShowDescriptions(false);
        }
    }

    protected class CommandLineSourceCodeEditorSuggester extends SourceCodeEditorSuggester {
    }

    public CommandLineSuggestionExtension getSuggestionExtension() {
        return (CommandLineSuggestionExtension) suggestionExtension;
    }

    @Override
    public void setApplyHandler(Runnable handler) {
        this.getSuggestionExtension().setApplyHandler(handler);
    }
}

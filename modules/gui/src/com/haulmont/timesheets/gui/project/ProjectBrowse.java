/*
 * Copyright (c) ${YEAR} ${PACKAGE_NAME}
 */
package com.haulmont.timesheets.gui.project;

import com.haulmont.bali.util.ParamsMap;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.ListComponent;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.components.TreeTable;
import com.haulmont.cuba.gui.components.actions.CreateAction;
import com.haulmont.cuba.gui.components.actions.EditAction;
import com.haulmont.timesheets.entity.Project;
import com.haulmont.timesheets.entity.Task;
import com.haulmont.timesheets.gui.ComponentsHelper;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.Map;

/**
 * @author gorelov
 */
public class ProjectBrowse extends AbstractLookup {

    @Inject
    protected TreeTable projectsTable;
    @Inject
    protected Table tasksTable;

    @Override
    public void init(Map<String, Object> params) {
        projectsTable.addAction(new CustomEditAction(projectsTable));
        tasksTable.addAction(new TaskCreateAction(tasksTable));
        tasksTable.addAction(new ComponentsHelper.TaskStatusTrackingAction(tasksTable, "switchStatus"));

        projectsTable.setStyleProvider(new Table.StyleProvider() {
            @Nullable
            @Override
            public String getStyleName(Entity entity, String property) {
                if ("status".equals(property)) {
                    Project project = (Project) entity;
                    return ComponentsHelper.getProjectStatusStyle(project);
                }
                return null;
            }
        });

        tasksTable.setStyleProvider(new Table.StyleProvider() {
            @Nullable
            @Override
            public String getStyleName(Entity entity, @Nullable String property) {
                if ("status".equals(property)) {
                    Task task = (Task) entity;
                    return ComponentsHelper.getTaskStatusStyle(task);
                }
                return null;
            }
        });
    }

    protected class CustomEditAction extends EditAction {

        public CustomEditAction(ListComponent target) {
            super(target);
        }

        @Override
        protected void afterCommit(Entity entity) {
            projectsTable.refresh();
        }
    }

    @Override
    public void ready() {
        projectsTable.expandAll();
    }

    protected class TaskCreateAction extends CreateAction {

        public TaskCreateAction(ListComponent target) {
            super(target);
        }

        @Override
        public Map<String, Object> getInitialValues() {
            return ParamsMap.of("project", projectsTable.getSingleSelected());
        }
    }
}
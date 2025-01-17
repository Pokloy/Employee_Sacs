package com.Employee_Sacs.app.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import com.Employee_Sacs.app.model.dao.entity.JoinedTaskUserInfo;
import com.Employee_Sacs.app.model.dao.entity.TaskEntity;

import jakarta.transaction.Transactional;

@Repository
public interface TaskDao extends JpaRepository<TaskEntity, Integer> {
    final String VIEW_ALL_TASK = "SELECT e FROM TaskEntity e";
    
    final String VIEW_ALL_WITH_CREATOR_AND_ASSIGNEE_NAME = " SELECT te.task_id, "
    		+ " uitc.employeeId, uitc.firstname, uita.employeeId, "
    		+ " uita.firstname, te.taskname, te.datestart, te.dateend, te.progress "
    		+ " FROM TaskEntity te "
    		+ " INNER JOIN UserInformationEntity uitc ON te.creator_id = uitc.employeeId "
    		+ " INNER JOIN UserInformationEntity uita ON te.assign_id = uita.employeeId ";

        final String UPDATE_A_CERTAIN_TASK = "UPDATE TaskEntity e "
            + "SET "
            + " taskname = :taskName, "
            + " datestart = :dateStart, "
            + " dateend = :dateEnd, "
            + " progress = :progress "
            + " WHERE task_id = :taskId";
            
        
        final String VIEW_ALL_TASK_BY_ASSIGN_ID =" SELECT te.task_id, "
        		+ " uitc.employeeId, uitc.firstname, uita.employeeId, "
        		+ " uita.firstname, te.taskname, te.datestart, te.dateend, te.progress "
        		+ " FROM TaskEntity te "
        		+ " INNER JOIN UserInformationEntity uitc ON te.creator_id = uitc.employeeId "
        		+ " INNER JOIN UserInformationEntity uita ON te.assign_id = uita.employeeId "
        		+ " WHERE te.assign_id = :assignId ";
        
        
    @Query(value = VIEW_ALL_TASK)
    public List<TaskEntity> viewAllTask() throws DataAccessException;
    
    @Query(value = VIEW_ALL_WITH_CREATOR_AND_ASSIGNEE_NAME)
    List<Object[]> getAllTaskWithNamesRaw() throws DataAccessException;
    
    default List<JoinedTaskUserInfo> getAllTaskWithNames(){
    	List<Object[]> taskObj = getAllTaskWithNamesRaw();
    	
    	if(taskObj.isEmpty()) {
    		return null;
    	}
    	
    	List<JoinedTaskUserInfo> result = new ArrayList<>();
    	for(Object[] row : taskObj) {
    		result.add(new JoinedTaskUserInfo(row));
    	}
    	
    	return result;
    }
    
    @Transactional
    @Modifying
    @Query(value = UPDATE_A_CERTAIN_TASK)
    public void updateTaskById(
           @Param("taskId") int taskId,
           @Param("taskName") String taskName,
           @Param("dateStart") String dateStart,
           @Param("dateEnd") String dateEnd,
           @Param("progress") String progress
    ) throws DataAccessException;
    
    
	@Query(value = VIEW_ALL_TASK_BY_ASSIGN_ID)
	List<Object[]> getSpecificTaskRaw(int assignId) throws DataAccessException;
	
	default List<JoinedTaskUserInfo> getSpecificTask(int assignId){
		List<Object[]> taskObj = getSpecificTaskRaw(assignId);
		
		if(taskObj.isEmpty()) {
			return null;
		}
		
		List<JoinedTaskUserInfo> result = new ArrayList<>();
		for(Object[] row : taskObj) {
			result.add(new JoinedTaskUserInfo(row));
		}
		
		return result;
	}
}

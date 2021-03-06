
package com.huayi.doupo.base.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.huayi.doupo.base.dal.base.DALFather;
import com.huayi.doupo.base.model.InstPlayerRechargeCallBack;
import com.huayi.doupo.base.model.player.PlayerMemObj;
import com.huayi.doupo.base.util.base.StringUtil;

public class InstPlayerRechargeCallBackDAL extends DALFather {
    
    @SuppressWarnings("rawtypes")
    public class ItemMapper implements RowMapper {
        
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			InstPlayerRechargeCallBack instPlayerRechargeCallBack = new InstPlayerRechargeCallBack();
            instPlayerRechargeCallBack.setId(rs.getInt("id"), 0);
            instPlayerRechargeCallBack.setInstPlayerId(rs.getInt("instPlayerId"), 0);
            instPlayerRechargeCallBack.setOpenId(rs.getString("openId"), 0);
            instPlayerRechargeCallBack.setPlayerName(rs.getString("playerName"), 0);
            instPlayerRechargeCallBack.setResultCode(rs.getInt("resultCode"), 0);
            instPlayerRechargeCallBack.setPayChannel(rs.getInt("payChannel"), 0);
            instPlayerRechargeCallBack.setPayState(rs.getInt("payState"), 0);
            instPlayerRechargeCallBack.setProvideState(rs.getInt("provideState"), 0);
            instPlayerRechargeCallBack.setSaveNum(rs.getInt("saveNum"), 0);
            instPlayerRechargeCallBack.setResultMsg(rs.getString("resultMsg"), 0);
            instPlayerRechargeCallBack.setExtendInfo(rs.getString("extendInfo"), 0);
            instPlayerRechargeCallBack.setOperDate(rs.getString("operDate"), 0);
            instPlayerRechargeCallBack.setOperTime(rs.getString("operTime"), 0);
            instPlayerRechargeCallBack.setVersion(rs.getInt("version"), 0);
			return instPlayerRechargeCallBack;
		}
	}
    
	public InstPlayerRechargeCallBack add(final InstPlayerRechargeCallBack model, int instPlayerId) throws Exception {
        try { 
            final String  updateTime = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
            StringBuilder strSql = new StringBuilder();
	        strSql.append(" insert into Inst_Player_RechargeCallBack (");
	        strSql.append("instPlayerId,openId,playerName,resultCode,payChannel,payState,provideState,saveNum,resultMsg,extendInfo,operDate,operTime,version");
	        strSql.append(" )");
	        strSql.append(" values (?,?,?,?,?,?,?,?,?,?,?,?,?) ");
            
            final String sql = strSql.toString();
            KeyHolder keyHolder = new GeneratedKeyHolder();
            
            this.getJdbcTemplate().update(new PreparedStatementCreator(){
                public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setInt(1, model.getInstPlayerId());
                    ps.setString(2, model.getOpenId());
                    ps.setString(3, model.getPlayerName());
                    ps.setInt(4, model.getResultCode());
                    ps.setInt(5, model.getPayChannel());
                    ps.setInt(6, model.getPayState());
                    ps.setInt(7, model.getProvideState());
                    ps.setInt(8, model.getSaveNum());
                    ps.setString(9, model.getResultMsg());
                    ps.setString(10, model.getExtendInfo());
                    ps.setString(11, model.getOperDate());
                    ps.setString(12, model.getOperTime());
                    ps.setInt(13, 0);
                    return ps;
                } 
            },keyHolder);
            
            model.setId(keyHolder.getKey().intValue());
            model.setVersion(0);
            PlayerMemObj playerMemObj = getPlayerMemObjByPlayerId(instPlayerId);
		    if (instPlayerId != 0 && isUseCach() && playerMemObj != null) {
                 playerMemObj.instPlayerRechargeCallBackMap.put(model.getId(), model);
		    }
          
		} catch (Exception e) {
			throw e;
		}
		return model;
	}
    
    public void batchAdd (final List<InstPlayerRechargeCallBack> list) {

        final String updateTime = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
        StringBuilder sql = new StringBuilder();
	    sql.append(" insert into Inst_Player_RechargeCallBack (");
	    sql.append("instPlayerId,openId,playerName,resultCode,payChannel,payState,provideState,saveNum,resultMsg,extendInfo,operDate,operTime,version");
	    sql.append(" )");
	    sql.append(" values (?,?,?,?,?,?,?,?,?,?,?,?,?) ");
            
	    BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter (){
	          public void setValues(PreparedStatement ps, int i) throws SQLException{
	        	   InstPlayerRechargeCallBack model = (InstPlayerRechargeCallBack)list.get(i);
                   ps.setInt(1, model.getInstPlayerId());
                   ps.setString(2, model.getOpenId());
                   ps.setString(3, model.getPlayerName());
                   ps.setInt(4, model.getResultCode());
                   ps.setInt(5, model.getPayChannel());
                   ps.setInt(6, model.getPayState());
                   ps.setInt(7, model.getProvideState());
                   ps.setInt(8, model.getSaveNum());
                   ps.setString(9, model.getResultMsg());
                   ps.setString(10, model.getExtendInfo());
                   ps.setString(11, model.getOperDate());
                   ps.setString(12, model.getOperTime());
                    ps.setInt(13, 0);
	          }
	          public int getBatchSize(){
	             return list.size();
	          }
	    };
		getJdbcTemplate().batchUpdate(sql.toString(), setter);
        
    }
    
	public int deleteById(int id, int instPlayerId) throws Exception {
		try {
            PlayerMemObj playerMemObj = getPlayerMemObjByPlayerId(instPlayerId);
			if (instPlayerId != 0 && isUseCach() && playerMemObj != null) {
                playerMemObj.instPlayerRechargeCallBackMap.remove(id);
			}
			String sql = "delete from Inst_Player_RechargeCallBack  where id = ?";
            Object[] params = new Object[]{id};
			return this.getJdbcTemplate().update(sql, params);
		} catch (Exception e) {
			throw e;
		}
	}
    
	public int deleteByWhere(String strWhere) throws Exception {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("delete from Inst_Player_RechargeCallBack where 1=1 ");

			if (strWhere != null && !strWhere.equals("")) {
				sql.append(" and " + strWhere);
			}
			return this.getJdbcTemplate().update(sql.toString());
		} catch (Exception e) {
			throw e;
		}
	}
    
	public int update(String sql) throws Exception {
		try {
			return this.getJdbcTemplate().update(sql.toString());
		} catch (Exception e) {
			throw e;
		}
	}
    
	public InstPlayerRechargeCallBack update(InstPlayerRechargeCallBack model, int instPlayerId) throws Exception {
		try {
            Object[] params = null;
            int version = model.getVersion() + 1;
            final String  updateTime = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
			StringBuffer sql = new StringBuffer("update Inst_Player_RechargeCallBack set ");
            
            sql.append("instPlayerId=?,openId=?,playerName=?,resultCode=?,payChannel=?,payState=?,provideState=?,saveNum=?,resultMsg=?,extendInfo=?,operDate=?,operTime=?,version=? ");
            
            sql.append("where id=? and version=?");
            params = new Object[] { model.getInstPlayerId(),model.getOpenId(),model.getPlayerName(),model.getResultCode(),model.getPayChannel(),model.getPayState(),model.getProvideState(),model.getSaveNum(),model.getResultMsg(),model.getExtendInfo(),model.getOperDate(),model.getOperTime(),version, model.getId(), model.getVersion() };

			int count = this.getJdbcTemplate().update(sql.toString(), params);
			if (count == 0) {
				throw new Exception("Concurrent Exception");
			}else{
				model.setVersion(version, 0);
                PlayerMemObj playerMemObj = getPlayerMemObjByPlayerId(instPlayerId);
				if (instPlayerId != 0 && isUseCach() && playerMemObj != null) {
	        		playerMemObj.instPlayerRechargeCallBackMap.put(model.getId(), model);
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return model;
	}
    
    @SuppressWarnings("unchecked")
	public InstPlayerRechargeCallBack getModel(int id, int instPlayerId) {
		try {
            PlayerMemObj playerMemObj = getPlayerMemObjByPlayerId(instPlayerId);
			if (instPlayerId != 0 && isUseCach() && playerMemObj != null) {
        		InstPlayerRechargeCallBack model = playerMemObj.instPlayerRechargeCallBackMap.get(id);
        		if (model == null) {
        			String sql = "select * from Inst_Player_RechargeCallBack where id=?";
				    Object[] params = new Object[]{id};
        			playerMemObj.instPlayerRechargeCallBackMap.put(id, (InstPlayerRechargeCallBack) this.getJdbcTemplate().queryForObject(sql, params, new ItemMapper()));
			    } else {
                    int cacheVersion = model.getVersion();
                    List<Map<String, Object>> list = sqlHelper("select version from Inst_Player_RechargeCallBack where id = " + id);
                    int dbVersion = (int)list.get(0).get("version");
                    if (cacheVersion != dbVersion) {
                        String sql = "select * from Inst_Player_RechargeCallBack where id=?";
                        Object[] params = new Object[]{id};
                        playerMemObj.instPlayerRechargeCallBackMap.put(id, (InstPlayerRechargeCallBack) this.getJdbcTemplate().queryForObject(sql, params, new ItemMapper()));
                    }
			    }
                
                model = playerMemObj.instPlayerRechargeCallBackMap.get(id);
                model.result = "";
        		return model;
			} else {
			    String sql = "select * from Inst_Player_RechargeCallBack where id=?";
			    Object[] params = new Object[]{id};
			    InstPlayerRechargeCallBack model = ( InstPlayerRechargeCallBack) this.getJdbcTemplate().queryForObject(sql, params, new ItemMapper());
			    model.result = "";
                return model;
			}
		} catch (DataAccessException e) {
			return null;
		}
	}
    
    @SuppressWarnings("unchecked")	
	public List<InstPlayerRechargeCallBack> getList(String strWhere, int instPlayerId) {
		StringBuffer sql = null;
        PlayerMemObj playerMemObj = getPlayerMemObjByPlayerId(instPlayerId);
		if (instPlayerId != 0 && isUseCach() && playerMemObj != null) {
    		sql = new StringBuffer("select id, version from Inst_Player_RechargeCallBack ");
		}else {
			sql = new StringBuffer("select * from Inst_Player_RechargeCallBack ");
		}
        if (StringUtil.isNotEmpty(strWhere)) {
			sql.append(" where " + strWhere);
		}
		if (instPlayerId != 0 && isUseCach() && playerMemObj != null) {
    		return listCacheCommonHandler(sql.toString(), instPlayerId);
    	} else {
		    List<InstPlayerRechargeCallBack> instPlayerRechargeCallBackList = (List<InstPlayerRechargeCallBack>) this.getJdbcTemplate().query(sql.toString(), new ItemMapper());
		    return instPlayerRechargeCallBackList;
		}
	}
    
	public List<Long> getListIdByWhere(String strWhere) throws Exception {
		try {
			List<Long> listLong = new ArrayList<Long>();
			StringBuffer sql = new StringBuffer("select id from Inst_Player_RechargeCallBack ");
			if (strWhere != null && !strWhere.equals("")) {
				sql.append(" where " + strWhere);
			}
			SqlRowSet rsSet = this.getJdbcTemplate().queryForRowSet(sql.toString());
			while (rsSet.next()) {
				listLong.add(rsSet.getLong("id"));
			}
			return listLong;
		} catch (Exception e) {
			throw e;
		}
	}
    
    @SuppressWarnings("unchecked")
	public List<InstPlayerRechargeCallBack> getListPagination(int index, int size, String strWhere, int instPlayerId) throws Exception {
		try {
			StringBuffer sql = null;
			PlayerMemObj playerMemObj = getPlayerMemObjByPlayerId(instPlayerId);
            if (instPlayerId != 0 && isUseCach() && playerMemObj != null) {
                sql = new StringBuffer("select id, version from Inst_Player_RechargeCallBack ");
            }else {
                sql = new StringBuffer("select * from Inst_Player_RechargeCallBack ");
            }
            if(index <= 0 || size <= 0){
				throw new Exception("index or size must bigger than zero");
			}else{
				index = (index - 1) * size;
			}
			if (StringUtil.isNotEmpty(strWhere)) {
			sql.append(" where " + strWhere);
		    }
			sql.append(" limit " + index + "," + size + "");
			if (instPlayerId != 0 && isUseCach() && playerMemObj != null) {
				return listCacheCommonHandler(sql.toString(), instPlayerId);
	    	} else {
			    return (List<InstPlayerRechargeCallBack>) this.getJdbcTemplate().query(sql.toString(), new ItemMapper());
			}
		} catch (Exception e) {
			throw e;
		}
	}
    
	public boolean isExist(long id, String strWhere) throws Exception {
		try {
			StringBuffer sql = new StringBuffer("select count(*) from Inst_Player_RechargeCallBack where id =?");
			if (strWhere != null && !strWhere.equals("")) {
				sql.append(" or " + strWhere);
			}
			int count = this.getJdbcTemplate().queryForObject(sql.toString(), Integer.class);
			return count > 0 ? true : false;
		} catch (Exception e) {
			throw e;
		}
	}
    
	public int getCount(String strWhere) throws Exception {
		try {
			StringBuffer sql = new StringBuffer("select count(*) from Inst_Player_RechargeCallBack");
			if (strWhere != null && !strWhere.equals("")) {
				sql.append(" where " + strWhere);
			}
			return this.getJdbcTemplate().queryForObject(sql.toString(), Integer.class);
		} catch (Exception e) {
			throw e;
		}
	}
    
    public List<Long> getCounts(String strWhere) throws Exception {
		try {
			List<Long> listLong = new ArrayList<Long>();
			StringBuffer sql = new StringBuffer("select count(*) as cnt from Inst_Player_RechargeCallBack ");
			if (strWhere != null && !strWhere.equals("")) {
				sql.append(strWhere);
			}
			SqlRowSet rsSet = this.getJdbcTemplate().queryForRowSet(sql.toString());
			while (rsSet.next()) {
				listLong.add(rsSet.getLong("cnt"));
			}
			return listLong;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public List<Map<String,Object>> sqlHelper(String sql) {
		return this.getJdbcTemplate().queryForList(sql);
	}
    
    @SuppressWarnings("unchecked")	
	public List<InstPlayerRechargeCallBack> getListFromMoreTale(String afterSql, int instPlayerId) {
		StringBuffer sql = null;
        PlayerMemObj playerMemObj = getPlayerMemObjByPlayerId(instPlayerId);
        if (instPlayerId != 0 && isUseCach() && playerMemObj != null) {
            sql = new StringBuffer("select a.id, a.version from Inst_Player_RechargeCallBack a ");
        }else {
            sql = new StringBuffer("select a.* from Inst_Player_RechargeCallBack a ");
        }
		if (StringUtil.isNotEmpty(afterSql)) {
		    sql.append(afterSql);
	    }
		if (instPlayerId != 0 && isUseCach() && playerMemObj != null) {
			return listCacheCommonHandler(sql.toString(), instPlayerId);
		} else {
		    return (List<InstPlayerRechargeCallBack>) this.getJdbcTemplate().query(sql.toString(), new ItemMapper());
		}
	}
    
	public List<Long> getListIdFromMoreTable(String afterSql) throws Exception {
		try {
			List<Long> listLong = new ArrayList<Long>();
			StringBuffer sql = new StringBuffer("select a.id from Inst_Player_RechargeCallBack a ");
			if (StringUtil.isNotEmpty(afterSql)) {
				sql.append(afterSql);
			}
			SqlRowSet rsSet = this.getJdbcTemplate().queryForRowSet(sql.toString());
			while (rsSet.next()) {
				listLong.add(rsSet.getLong("id"));
			}
			return listLong;
		} catch (Exception e) {
			throw e;
		}
	}
    
	public int getStatResult(String statType, String conParam, String strWhere) throws Exception {
		int result = 0;
		try {
            StringBuffer sql = new StringBuffer("select "+statType+"("+conParam+") from  Inst_Player_RechargeCallBack");
			if (strWhere != null && !strWhere.equals("")) {
				sql.append(" where " + strWhere);
			}
			return this.getJdbcTemplate().queryForObject(sql.toString(), Integer.class);
		} catch (Exception e) {
			return result;
		}
	}

	private List<InstPlayerRechargeCallBack> listCacheCommonHandler(String sql, int instPlayerId){
		List<InstPlayerRechargeCallBack> modelList = new ArrayList<InstPlayerRechargeCallBack>();
		PlayerMemObj playerMemObj = getPlayerMemObjByPlayerId(instPlayerId);
		SqlRowSet rsSet = this.getJdbcTemplate().queryForRowSet(sql.toString());
		while (rsSet.next()) {
			int id = rsSet.getInt("id");
			int dbVersion = rsSet.getInt("version");
			InstPlayerRechargeCallBack model = playerMemObj.instPlayerRechargeCallBackMap.get(id);
			if (model == null) {
				model = getModel(id, instPlayerId);
                model.result = "";
				modelList.add(model);
			} else {
				int cacheVersion = model.getVersion();
				if (cacheVersion != dbVersion) {
					model = getModel(id, instPlayerId);
				}
                model.result = "";
				modelList.add(model);
			}
		}
		return modelList;
	}
}



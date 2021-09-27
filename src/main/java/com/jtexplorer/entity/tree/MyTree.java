package com.jtexplorer.entity.tree;

import com.baomidou.mybatisplus.annotation.TableField;
import com.jtexplorer.util.ListUtil;
import com.jtexplorer.util.StringUtil;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public abstract class MyTree<M extends MyTree<M>> {
    @TableField(exist = false)
    private Long id;
    @TableField(exist = false)
    private Long pId;
    @TableField(exist = false)
    private String name;
    @TableField(exist = false)
    private String parentName;
    @TableField(exist = false)
    private List<M> children;
    @TableField(exist = false)
    private M parent;
    /**
     * 该权限是否是叶子节点（无下级节点）
     */
    @TableField(exist = false)
    private Boolean leaf = true;
    /**
     * 该权限是否是根节点（无上级节点）
     */
    @TableField(exist = false)
    private Boolean rootIs;

    /**
     * 本类型数量
     */
    @TableField(exist = false)
    private BigDecimal quantity;

    /**
     * 子类型数量
     */
    @TableField(exist = false)
    private BigDecimal childQuantity;

    /**
     * 总数量
     */
    @TableField(exist = false)
    private BigDecimal allQuantity;

    public void buildQ() {
        if (StringUtil.isEmpty(getQuantity())) {
            setQuantity(BigDecimal.ZERO);
        }
        if (StringUtil.isEmpty(getChildQuantity())) {
            setChildQuantity(BigDecimal.ZERO);
        }
        if (StringUtil.isEmpty(getAllQuantity())) {
            setAllQuantity(getQuantity());
        }
    }

    /**
     * 应至少实现this.setId();
     * this.setPId();
     */
    public abstract void setIdAndPid();

    public String getAllName() {
        if (getParent() != null) {
            return getParent().getAllName() + "/" + getName();
        } else {
            return getName();
        }
    }

    /**
     * @param myTrees   数据
     * @param queryType 查询类型：P查询上级，C查询下级，PAndC查询上级和下级
     * @return List<MyTree>
     */
    public List<M> myObjectToTreeUtilForChildren(List<M> myTrees, String queryType) {
        // 根列表
        try {
            List<M> treeList = new ArrayList<>();
            List<M> list = myTrees;
            list.forEach(M::setIdAndPid);
            for (M myTree : list) {
                if (StringUtil.isNotEmpty(myTree.getPId())) {
                    myTree.setRootIs(true);
                    for (M myTreeTwo : list) {
                        if (StringUtil.isNotEmpty(myTreeTwo.getId())) {
                            if (myTree.getPId().equals(myTreeTwo.getId())) {
                                if ("C".equals(queryType)) {
                                    if (ListUtil.isEmpty(myTreeTwo.getChildren()))
                                        myTreeTwo.setChildren(new ArrayList<>());
                                    myTreeTwo.getChildren().add(myTree);

                                }
                                if ("P".equals(queryType)) {
                                    myTree.setParent(myTreeTwo);
                                    myTree.setParentName(myTreeTwo.getParentName());
                                    myTreeTwo.setLeaf(false);
                                }
                                myTree.setRootIs(false);
                                myTree.setParentName(myTreeTwo.getParentName());
                                break;
                            }
                        }
                    }
                }
            }
            // 将根节点放进根列表中返回
            list.forEach(v -> {
                // 查看是否是叶子节点

                if ("C".equals(queryType)) {
                    v.setLeaf(ListUtil.isEmpty(v.getChildren()));
                    if (v.getRootIs()) {
                        treeList.add(v);
                    }
                }
                if ("P".equals(queryType)) {
                    if (v.getLeaf()) {
                        treeList.add(v);
                    }
                }


            });
            return treeList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 只返回根节点用的（解决因返回值是父节点，导致子节点无法接取该方法返回的值，因此增加一个返回用的参数result）
     *
     * @param myTrees   数据
     * @param queryType 查询类型：P查询上级，C查询下级，PAndC查询上级和下级
     * @return List<MyTree>
     */
    public List<M> myObjectToTreeUtilForChildren(List<M> myTrees, String queryType, List<M> result) {
        // 根列表
        try {
            List<M> list = myTrees;
            list.forEach(M::setIdAndPid);
            for (M myTree : list) {
                if (StringUtil.isNotEmpty(myTree.getPId())) {
                    myTree.setRootIs(true);
                    for (M myTreeTwo : list) {
                        if (StringUtil.isNotEmpty(myTreeTwo.getId())) {
                            if (myTree.getPId().equals(myTreeTwo.getId())) {
                                if ("C".equals(queryType)) {
                                    if (ListUtil.isEmpty(myTreeTwo.getChildren()))
                                        myTreeTwo.setChildren(new ArrayList<>());
                                    myTreeTwo.getChildren().add(myTree);
                                }
                                if ("P".equals(queryType)) {
                                    myTree.setParent(myTreeTwo);
                                    myTreeTwo.setLeaf(false);
                                }
                                myTree.setRootIs(false);
                                myTree.setParentName(myTreeTwo.getParentName());
                                break;
                            }
                        }
                    }
                }
            }
            // 将根节点放进根列表中返回
            list.forEach(v -> {
                // 查看是否是叶子节点

                if ("C".equals(queryType)) {
                    v.setLeaf(ListUtil.isEmpty(v.getChildren()));
                    if (v.getRootIs()) {
                        result.add(v);
                    }
                }
                if ("P".equals(queryType)) {
                    if (v.getLeaf()) {
                        result.add(v);
                    }
                }


            });
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

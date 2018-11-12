#include <stdio.h>
#include <stdlib.h>

typedef int TElementType;

typedef struct BiTNode {
    int data;
    struct BiTNode *lChild;
    struct BiTNode *rChild;
} BiTNode;

typedef BiTNode* BiTree;

// 先序创建二叉树
void CreateBiTree(BiTree *T) {
    TElementType ch;
    scanf("%d", &ch);
    if (ch == -1) {
        *T = NULL;
        return;
    }
    else
    {
        *T = (BiTree) malloc(sizeof(BiTNode));  // 申请内存空间
        (*T)->data = ch;
        printf("输入%d的左子节点：",ch);
        CreateBiTree(&(*T)->lChild);  // 构造左子树
        printf("输入%d的右子节点：",ch);
        CreateBiTree(&(*T)->rChild);  // 构造右子树
    }
}

// 先序遍历二叉树（递归）
void PreOrderTraverse(BiTree T) {

    if (T == NULL) {
        return;
    }
    printf("%d", T->data);

    PreOrderTraverse(T->lChild);
    PreOrderTraverse(T->rChild);
}

// 中序遍历二叉树
void InOrderTraverse(BiTree T) {

    if (T == NULL) {
        return;
    }

    InOrderTraverse(T->lChild);
    printf("%d", T->data);
    InOrderTraverse(T->rChild);
}

// 后序遍历二叉树
void PostOrderTraverse(BiTree T) {
    if (T == NULL) {
        return;
    }

    PostOrderTraverse(T->lChild);
    PostOrderTraverse(T->rChild);
    printf("%d", T->data);
}

// 二叉树的深度
int TreeDeep(BiTree T) {

    int deep = 0;
    if (T != NULL) {
        int leftDeep = TreeDeep(T->lChild);
        int rightDeep = TreeDeep(T->rChild);
        deep = leftDeep >= rightDeep ? leftDeep + 1 : rightDeep + 1;
    }

    return deep;
}

// 叶子节点的个数
int LeafCount(BiTree T) {
    int count = 0;
    if (T != NULL) {
        if (T->rChild == NULL && T->lChild == NULL) {
            count++;
        }

        LeafCount(T->lChild);
        LeafCount(T->rChild);
    }
    return count;
}

int main() {

    BiTree bTree;

    printf("请输入第一个节点的值，-1表示没有叶节点：\n");

    CreateBiTree(&bTree);

    printf("先序遍历二叉树：\n");
    PreOrderTraverse(bTree);

    printf("中序遍历二叉树：\n");
    InOrderTraverse(bTree);

    printf("后序遍历二叉树：\n");
    PostOrderTraverse(bTree);

    printf("树的深度为: %d \n", TreeDeep(bTree));

    printf("叶子节点个数为：%d \n", LeafCount(bTree));

    return 0;
}
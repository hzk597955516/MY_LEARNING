.globl matmul

.text
# =======================================================
# FUNCTION: Matrix Multiplication of 2 integer matrices
# 	d = matmul(m0, m1)
#   The order of error codes (checked from top to bottom):
#   If the dimensions of m0 do not make sense, 
#   this function exits with exit code 2.
#   If the dimensions of m1 do not make sense, 
#   this function exits with exit code 3.
#   If the dimensions don't match, 
#   this function exits with exit code 4.
# Arguments:
# 	a0 (int*)  is the pointer to the start of m0 
#	a1 (int)   is the # of rows (height) of m0
#	a2 (int)   is the # of columns (width) of m0
#	a3 (int*)  is the pointer to the start of m1
# 	a4 (int)   is the # of rows (height) of m1
#	a5 (int)   is the # of columns (width) of m1
#	a6 (int*)  is the pointer to the the start of d
# Returns:
#	None (void), sets d = matmul(m0, m1)
# =======================================================
matmul:

    # Error checks
    li t0, 1
    blt a1, t0, exit_2
    blt a2, t0, exit_2
    blt a4, t0, exit_3
    blt a5, t0, exit_3
    bne a2, a4, exit_4

    # Prologue
    addi sp, sp, -24
    sw ra, 20(sp)
    sw s0, 16(sp)
    sw s1, 12(sp)
    sw s2, 8(sp)
    sw s3, 4(sp)
    sw s4, 0(sp)

    mv s0, a0
    mv s1, a1
    mv s2, a2
    mv s3, a3
    mv s4, a4

    li t0, 0
    li t1, 0
outer_loop_start:
    bge t0, s1, outer_loop_end
    mul t3, t0, s2
    slli t3, t3, 2
    add t3, t3, s0
inner_loop_start:
    bge t1, a5, inner_loop_end
    slli t4, t1, 2
    add t4, t4, s3
    mv a0, t3
    mv a1, t4
    mv a2, s2
    li a3, 1
    mv a4, a5
    addi sp, sp, -24
    sw t0, 20(sp)
    sw t1, 16(sp)
    sw t2, 12(sp)
    sw t3, 8(sp)
    sw t4, 4(sp)
    sw t5, 0(sp)
    jal ra, dot
    lw t0, 20(sp)
    lw t1, 16(sp)
    lw t2, 12(sp)
    lw t3, 8(sp)
    lw t4, 4(sp)
    lw t5, 0(sp)
    addi sp, sp, 24
    mul t6, t0, a5
    add t6, t6, t1
    slli t6, t6, 2
    add t6, t6, a6
    sw a0, 0(t6)
    addi t1, t1, 1
    j inner_loop_start
inner_loop_end:
    addi t0, t0, 1
    li t1, 0
    j outer_loop_start

outer_loop_end:
    lw ra, 20(sp)
    lw s0, 16(sp)
    lw s1, 12(sp)
    lw s2, 8(sp)
    lw s3, 4(sp)
    lw s4, 0(sp)
    addi sp, sp, 24
    # Epilogue
    ret

exit_2:
    li a0, 17
    li a1, 2
    ecall
exit_3:
    li a0, 17
    li a1, 3
    ecall
exit_4:
    li a0, 17
    li a1, 4
    ecall

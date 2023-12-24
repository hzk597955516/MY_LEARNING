.globl dot

.text
# =======================================================
# FUNCTION: Dot product of 2 int vectors
# Arguments:
#   a0 (int*) is the pointer to the start of v0
#   a1 (int*) is the pointer to the start of v1
#   a2 (int)  is the length of the vectors
#   a3 (int)  is the stride of v0
#   a4 (int)  is the stride of v1
# Returns:
#   a0 (int)  is the dot product of v0 and v1
#
# If the length of the vector is less than 1, 
# this function exits with error code 5.
# If the stride of either vector is less than 1,
# this function exits with error code 6.
# =======================================================
dot:

    # Prologue
    addi sp, sp, -4
    sw ra, 0(sp)
    
    addi t0, x0, 1
    blt a2, t0, exit_5
    blt a3, t0, exit_6
    blt a4, t0, exit_6
    
    addi t0, x0, 0
    addi t1, x0, 0
loop_start:
    bge t1, a2, loop_end
    slli t3, t1, 2
    mul t4, t3, a3
    mul t5, t3, a4
    add t4, t4, a0
    add t5, t5, a1
    lw t4, 0(t4)
    lw t5, 0(t5)
    mul t4, t4, t5
    add t0, t0, t4
    addi t1, t1, 1
    j loop_start
loop_end:
    lw ra, 0(sp)
    addi sp, sp, 4
    add a0, t0, x0
    # Epilogue
    ret

exit_5:
    li a0, 17
    li a1, 5
    ecall
exit_6:
    li a0, 17
    li a1, 6
    ecall